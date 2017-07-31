package tourguide.logica;


import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.data.Binder;
import com.vaadin.server.ExternalResource;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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
public class HotelEditor extends VerticalLayout {

	

	private final HotelRepository hotelRepository;

	/**
	 */
	private Hotel hotel;

	/* Fields to edit properties in hotel entity */
	
	VerticalLayout descripcionItem = new VerticalLayout();
	HorizontalLayout panelItem = new HorizontalLayout();
	
	Label nombre = new Label("nombre");
	
	Label estrellas = new Label("estrellas");

	Image imagenHotel = new Image();

	Label direccion = new Label("direccion");

	Label valoracion = new Label("valoracion");

	Label km_playa = new Label("km_playa");
	
	Label preio_medio_noche = new Label("precio_medio_noche");

	Label descripcion = new Label("descripcion");

	CssLayout actions = new CssLayout();

	Binder<Hotel> binder = new Binder<>(Hotel.class);
	
	private GoogleMap ubicacion = new GoogleMap(null, null, null);
	private GoogleMapMarker marcador;

	
	
	@Autowired
	public HotelEditor(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;

		nombre.setSizeFull();
//		descripcion.setSizeFull();
		ubicacion.setEnabled(false);
		//descripcionItem.addComponents(nombre);
		//panelItem.addComponents(descripcionItem);
		
		descripcionItem.addComponents(nombre,estrellas,direccion,valoracion,km_playa, descripcion);
		panelItem.addComponents(imagenHotel,descripcionItem);

		addComponents(panelItem);

		// bind using naming convention

		// Configure and style components
		setSpacing(true);
//		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		this.setSizeFull();

		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public void editHotel(Hotel hotelNuevo) {

		hotel = hotelNuevo;
		String imgUrl = hotel.getImagenRecurso();
		imagenHotel = setWeblImage(imgUrl, imagenHotel);
		imagenHotel.setVisible(true);
		
		//Seteamos valores a mostrar antes de que sea visible
		//latitud.setValue(hotelNuevo.getLatitud().toString());
		//longitud.setValue(hotelNuevo.getLongitud().toString());
		nombre.setValue(hotelNuevo.getNombre());
		estrellas.setValue("Estrellas: " + hotelNuevo.getEstrellas());
		direccion.setValue("Dirección: " + hotelNuevo.getDireccion());
		valoracion.setValue("Contacto: " + hotelNuevo.getValoracion());
		preio_medio_noche.setValue("Precio medio: " + hotelNuevo.getPrecio_medio_noche());
		km_playa.setValue("Horario: " + hotelNuevo.getKm_playa());
		descripcion.setValue(hotelNuevo.getDescripcion());
		descripcion.setWidth("300px");

		
		setVisible(true);
	}

	private Image setWeblImage(String urlImg, Image imagenL) {

		ExternalResource externalResource = new ExternalResource(urlImg);

		imagenL.setSource(externalResource);
		imagenL.setWidth("500px");
		imagenL.setHeight("500px");
		return imagenL;
	}
	

}