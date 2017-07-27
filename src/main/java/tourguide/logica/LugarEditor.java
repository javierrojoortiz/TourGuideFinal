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
	TextField nombreLugar = new TextField();
	TextField tipo = new TextField("Tipo");

	TextField imagenRecurso = new TextField("Imagen");

	Image imagenLugar = new Image();

	TextField direccion = new TextField("Dirección: ");

	TextField telefono = new TextField("Teléfono: ");

	TextField horario = new TextField("Horario de apertura: ");

	CheckBox visitaGuiada = new CheckBox("¿Tiene visitas guiadas?");

	TextField descripcion = new TextField("Descripción");

	CssLayout actions = new CssLayout();

	Binder<Lugar> binder = new Binder<>(Lugar.class);

	VerticalLayout layoutIzqda = new VerticalLayout();

	@Autowired
	public LugarEditor(LugarRepository lugarRepository) {
		this.lugarRepository = lugarRepository;

		nombreLugar.setSizeFull();
		
		nombreLugar.setReadOnly(true);

		tipo.setEnabled(false);
		imagenRecurso.setEnabled(false);
		direccion.setEnabled(false);
		telefono.setEnabled(false);
		horario.setEnabled(false);
		visitaGuiada.setEnabled(false);
		descripcion.setEnabled(false);
		descripcion.setSizeFull();

		addComponents(nombreLugar, imagenLugar);

		addComponents(layoutIzqda, tipo, imagenRecurso, direccion, telefono, horario, visitaGuiada,
				descripcion);

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

	public String editLugar(Lugar l) {
		if (l == null) {
			setVisible(false);
		//	return;
		}
		final boolean persisted = l.getId() != null;
		final boolean tieneImagen = l.getImagenRecurso() != null;
		String imgUrl="";
		
		if (persisted) {
			// Find fresh entity for editing
			lugar = lugarRepository.findOne(l.getId());
			if (tieneImagen) {
				imgUrl = lugar.getImagenRecurso();
				imagenLugar = setWeblImage(imgUrl, imagenLugar);
				imagenLugar.setVisible(true);
			}else {imagenLugar.setVisible(false);}

		} else {
			lugar = l;
		}
		// cancel.setVisible(persisted);

		// Bind lugar properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(lugar);

		setVisible(true);

		// A hack to ensure the whole form is visible
		// save.focus();
		// Select all text in nombreLugar field automatically
		nombreLugar.selectAll();
		return imgUrl;
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
