package tourguide.logica;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
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

	// public TextField getImagenRecurso() {
	// return imagenRecurso;
	// }
	//
	// public void setImagenRecurso(TextField imagenRecurso) {
	// this.imagenRecurso = imagenRecurso;
	// }

	private final LugarRepository lugarRepository;

	/**
	 * The currently edited lugar
	 */
	private Lugar lugar;

	/* Fields to edit properties in lugar entity */
	
	VerticalLayout descripcionItem = new VerticalLayout();
	HorizontalLayout panelItem = new HorizontalLayout();
	
	Label nombreLugar = new Label();
	
	Label tipo = new Label();

	Image imagenLugar = new Image();

	Label direccion = new Label();

	Label telefono = new Label();

	Label horario = new Label();

	CheckBox visitaGuiada = new CheckBox("¿Tiene visitas guiadas?");

	Label descripcion = new Label();

	CssLayout actions = new CssLayout();

	Binder<Lugar> binder = new Binder<>(Lugar.class);

	@Autowired
	public LugarEditor(LugarRepository lugarRepository) {
		this.lugarRepository = lugarRepository;

		nombreLugar.setSizeFull();
		descripcion.setSizeFull();
		
		descripcionItem.addComponents(nombreLugar,tipo,direccion,telefono,horario,visitaGuiada, descripcion);
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
	
		nombreLugar.setValue(lugarNuevo.getNombreLugar());
		tipo.setValue(lugarNuevo.getTipo());
		direccion.setValue("Dirección: " + lugarNuevo.getDireccion());
		telefono.setValue("Contacto: " + lugarNuevo.getTelefono());
		horario.setValue("Horario: " + lugarNuevo.getHorario());
		descripcion.setValue(lugarNuevo.getDescripcion());
		descripcion.setWidth("300px");
		visitaGuiada.setValue(lugarNuevo.isVisitaGuiada());
		visitaGuiada.setEnabled(false);

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

}
