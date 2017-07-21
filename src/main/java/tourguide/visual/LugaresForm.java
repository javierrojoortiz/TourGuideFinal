package tourguide.visual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;

import es.cic.taller.ejercicio09.MyUI;
import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;

public class LugaresForm extends FormLayout  {

	private LugarRepository repoLugar;

	private LugarEditor lugarEditor;

	Grid<Lugar> grid;

	TextField filter;

	private Button addNewBtn;
	
	MenuBar menubar;
	
	private HorizontalLayout horizontalLayout = new HorizontalLayout();
	
	private VaadinUI vaadinUI;
	
	public LugaresForm(VaadinUI vaadinUI) {
		this.vaadinUI = vaadinUI;

		horizontalLayout.addComponents(grid, addNewBtn);
		addComponents(horizontalLayout);
	}	
	public LugaresForm() {};
	

	@Autowired
	public LugaresForm(LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.grid = new Grid<>(Lugar.class);
		this.menubar = new MenuBar();
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Lugar", FontAwesome.PLUS);
	}

	//@Override
	protected void initLugares() {
		HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(menubarLayout,actions, grid, lugarEditor);
	//	setContent(mainLayout);
		
		menubar.setWidth(100.0f, Unit.PERCENTAGE);
		menubar.addItem("Hoteles", null, null);
		menubar.addItem("Restaurantes", null, null);

		grid.setWidth(700, Unit.PIXELS);
		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "nombreLugar", "tipo");

		filter.setPlaceholder("Filtrar por tipo");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			lugarEditor.editLugar(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> lugarEditor.editLugar(new Lugar("", "")));

		// Listen changes made by the editor, refresh data from backend
		lugarEditor.setChangeHandler(() -> {
			lugarEditor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}
	
    private final Command menuCommand = selectedItem -> selectedItem.getText();
   

	// tag::listLugares[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repoLugar.findAll());
		}
		else {
			grid.setItems(repoLugar.findByTipoStartsWithIgnoreCase(filterText));
		}
	}
	// end::listLugares[]

}


