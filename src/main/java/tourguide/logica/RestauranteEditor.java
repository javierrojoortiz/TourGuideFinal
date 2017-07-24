package tourguide.logica;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class RestauranteEditor extends VerticalLayout{
	private final LugarRepository lugarRepository;

	/**
	 * The currently edited lugar
	 */
	private Lugar lugar;

	/* Fields to edit properties in lugar entity */
	TextField nombreLugar = new TextField("Nombre del Lugar");
	TextField tipo = new TextField("Tipo");

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Lugar> binder = new Binder<>(Lugar.class);

	@Autowired
	public RestauranteEditor(LugarRepository lugarRepository) {
		this.lugarRepository = lugarRepository;

		addComponents(nombreLugar, tipo, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> lugarRepository.save(lugar));
		delete.addClickListener(e -> lugarRepository.delete(lugar));
		cancel.addClickListener(e -> editLugar(lugar));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editLugar(Lugar l) {
		if (l == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = l.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			lugar = lugarRepository.findOne(l.getId());
		}
		else {
			lugar = l;
		}
		cancel.setVisible(persisted);

		// Bind lugar properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(lugar);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in nombreLugar field automatically
		nombreLugar.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}