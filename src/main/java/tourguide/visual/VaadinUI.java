package tourguide.visual;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.Hotel;
import tourguide.logica.HotelEditor;
import tourguide.logica.HotelRepository;
import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;

@SpringUI
public class VaadinUI extends UI {

	private final LugarRepository repoLugar;

	private final LugarEditor lugarEditor;

	final Grid<Lugar> gridLugar;

	final TextField filter;

	private final Button addNewBtn;

	private final Image img_inicio = new Image();
	
	MenuBar menubar;

	private LugaresForm lugaresform = new LugaresForm(this);

	Logger logger = Logger.getLogger(VaadinUI.class);
	
	@Autowired
	public VaadinUI(LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.gridLugar = new Grid<>(Lugar.class);
		this.menubar = new MenuBar();
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Lugar", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
		// build layout
		Panel panelLugar = new Panel("Panel Lugar");
		
		img_inicio.setSource(getImageResource("inicio.jpg"));
		
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(menubarLayout,img_inicio, panelLugar);
		setContent(mainLayout);
		

		// Define a common menu command for all the menu items.
		MenuBar.Command commandVerPanelLugar = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				panelLugar.setVisible(true);
				img_inicio.setVisible(false);
			}
		};
		
		MenuBar.Command commandInicio = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				img_inicio.setVisible(true);
			}
		};

		menubar.setWidth(100.0f, Unit.PERCENTAGE);
		menubar.addItem("Inicio", VaadinIcons.HOME , commandInicio);
		menubar.addItem("Hoteles", null, null);
		menubar.addItem("Restaurantes", null, null);
		menubar.addItem("Lugares", null, commandVerPanelLugar);

		panelLugar.addStyleName("mypanelexample");
		panelLugar.setSizeUndefined(); // Shrink to fit content
		mainLayout.addComponent(panelLugar);
		panelLugar.setVisible(false);

		LugaresForm contenidoPanelLugar = new LugaresForm(this);

		contenidoPanelLugar.addComponent(actions);
		contenidoPanelLugar.addStyleName("mipanel de contenidos");
		contenidoPanelLugar.addComponent(gridLugar);
		contenidoPanelLugar.setSizeUndefined(); // Shrink to fit
		contenidoPanelLugar.setMargin(true);
		contenidoPanelLugar.addComponent(lugarEditor);
		panelLugar.setContent(contenidoPanelLugar);

		gridLugar.setWidth(700, Unit.PIXELS);
		gridLugar.setHeight(300, Unit.PIXELS);
		gridLugar.setColumns("id", "nombreLugar", "tipo");

		filter.setPlaceholder("Filtrar por tipo");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listLugares(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		gridLugar.asSingleSelect().addValueChangeListener(e -> {
			lugarEditor.editLugar(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> lugarEditor.editLugar(new Lugar("", "")));

		// Listen changes made by the editor, refresh data from backend
		lugarEditor.setChangeHandler(() -> {
			lugarEditor.setVisible(false);
			listLugares(filter.getValue());
		});

		// Initialize listing
		listLugares(null);

	}

	private final Command menuCommand = selectedItem -> selectedItem.getText();

	// tag::listLugares[]
	void listLugares(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			gridLugar.setItems(repoLugar.findAll());
		} else {
			gridLugar.setItems(repoLugar.findByTipoStartsWithIgnoreCase(filterText));
		}
	}
	// end::listLugares[]
	private Resource getImageResource(String recurso) {

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		logger.debug("Ruta: " + basepath);
		FileResource resource = new FileResource(new File (basepath + "/images/" + recurso));
		return resource;
	}

}

// @SpringUI
// public class VaadinUI extends UI {
//
// private final CustomerRepository repo;
//
// private final CustomerEditor editor;
//
// final Grid<Customer> grid;
//
// final TextField filter;
//
// private final Button addNewBtn;
//
// final MenuBar menubar;
//
//
// @Autowired
// public VaadinUI(CustomerRepository repo, CustomerEditor editor) {
// this.repo = repo;
// this.editor = editor;
// this.grid = new Grid<>(Customer.class);
// this.menubar = new MenuBar();
// this.filter = new TextField();
// this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
// }
//
// @Override
// protected void init(VaadinRequest request) {
// HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
// // build layout
// HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
// VerticalLayout mainLayout = new VerticalLayout(menubarLayout,actions, grid,
// editor);
// setContent(mainLayout);
//
// menubar.setWidth(100.0f, Unit.PERCENTAGE);
// menubar.addItem("Lugares", null, null);
// menubar.addItem("Restaurantes", null, null);
//
// grid.setHeight(300, Unit.PIXELS);
// grid.setColumns("id", "firstName", "lastName");
//
// filter.setPlaceholder("Filter by last name");
//
// // Hook logic to components
//
// // Replace listing with filtered content when user changes filter
// filter.setValueChangeMode(ValueChangeMode.LAZY);
// filter.addValueChangeListener(e -> listCustomers(e.getValue()));
//
// // Connect selected Customer to editor or hide if none is selected
// grid.asSingleSelect().addValueChangeListener(e -> {
// editor.editCustomer(e.getValue());
// });
//
// // Instantiate and edit new Customer the new button is clicked
// addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
//
// // Listen changes made by the editor, refresh data from backend
// editor.setChangeHandler(() -> {
// editor.setVisible(false);
// listCustomers(filter.getValue());
// });
//
// // Initialize listing
// listCustomers(null);
// }
//
// private final Command menuCommand = selectedItem -> selectedItem.getText();
//
//
// // tag::listCustomers[]
// void listCustomers(String filterText) {
// if (StringUtils.isEmpty(filterText)) {
// grid.setItems(repo.findAll());
// }
// else {
// grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
// }
// }
// // end::listCustomers[]
//
// }
