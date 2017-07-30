package tourguide.logica;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import tourguide.visual.VaadinUI;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.InfoWindowClosedListener;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form
 * in multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for
 * all your forms - less code, better UX. See e.g. AbstractForm in Viritin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class LugarEditor extends VerticalLayout {

	

	private final LugarRepository lugarRepository;

	/**
	 * The currently edited lugar
	 */
	private Lugar lugar;

	/* Fields to edit properties in lugar entity */
	
	VerticalLayout descripcionItem = new VerticalLayout();
	HorizontalLayout panelItem = new HorizontalLayout();
	HorizontalLayout panelMapa = new HorizontalLayout();
	Label nombreLugar = new Label();
	
	Label tipo = new Label();

	Image imagenLugar = new Image();

	Label direccion = new Label();

	Label telefono = new Label();

	Label horario = new Label();

	CheckBox visitaGuiada = new CheckBox("¿Tiene visitas guiadas?");

	Label descripcion = new Label();
	
	Label latitud= new Label();
	
	Label longitud= new Label();

	CssLayout actions = new CssLayout();

	Binder<Lugar> binder = new Binder<>(Lugar.class);
	
	private GoogleMap ubicacion = new GoogleMap(null, null, null);
	private GoogleMapMarker marcador;

	
	
	@Autowired
	public LugarEditor(LugarRepository lugarRepository) {
		this.lugarRepository = lugarRepository;

		nombreLugar.setSizeFull();
		descripcion.setSizeFull();
		ubicacion.setEnabled(false);
		panelMapa.addComponent(ubicacion);
		descripcionItem.addComponents(nombreLugar,tipo,direccion,telefono,horario,visitaGuiada, descripcion,panelMapa);
		panelItem.addComponents(imagenLugar,descripcionItem);

		addComponents(panelItem);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		this.setSizeFull();

		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public void editLugar(Lugar lugarNuevo) {

		
		lugar = lugarNuevo;
		String imgUrl = lugar.getImagenRecurso();
		imagenLugar = setWeblImage(imgUrl, imagenLugar);
		imagenLugar.setVisible(true);
		
			
		// cancel.setVisible(persisted);

		// Bind lugar properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(lugar);
		
		
		//Seteamos valores a mostrar antes de que sea visible
		latitud.setValue(lugarNuevo.getLatitud().toString());
		longitud.setValue(lugarNuevo.getLongitud().toString());
		nombreLugar.setValue(lugarNuevo.getNombreLugar());
		tipo.setValue(lugarNuevo.getTipo());
		direccion.setValue("Dirección: " + lugarNuevo.getDireccion());
		telefono.setValue("Contacto: " + lugarNuevo.getTelefono());
		horario.setValue("Horario: " + lugarNuevo.getHorario());
		descripcion.setValue(lugarNuevo.getDescripcion());
		descripcion.setWidth("300px");
		visitaGuiada.setValue(lugarNuevo.isVisitaGuiada());
		visitaGuiada.setEnabled(false);
		
		ubicacion=ubicarMarcador(lugarNuevo);
		ubicacion.setVisible(true);
		
		setVisible(true);
	}

	// public void setChangeHandler(ChangeHandler h) {
	// // ChangeHandler is notified when either save or delete
	// // is clicked
	// save.addClickListener(e -> h.onChange());
	// delete.addClickListener(e -> h.onChange());
	// }

	private Image setWeblImage(String urlImg, Image imagenL) {

		ExternalResource externalResource = new ExternalResource(urlImg);

		imagenL.setSource(externalResource);
		imagenL.setWidth("500px");
		imagenL.setHeight("500px");
		return imagenL;
	}
	
	
	private GoogleMap ubicarMarcador(Lugar l) {
		ubicacion.setCenter(new LatLon(l.getLatitud(),l.getLongitud()));
		ubicacion.setZoom(18);
		ubicacion.setWidth("500px");
		ubicacion.setHeight("500px");

		marcador = new GoogleMapMarker();
		marcador.setPosition(new LatLon(l.getLatitud(),l.getLongitud()));
		return ubicacion;
	}

}
