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

	private final Image img_inicio = new Image();
	
	MenuBar menubar;
	
	private final LugarRepository repoLugar;

	private final LugarEditor lugarEditor;

	Logger logger = Logger.getLogger(VaadinUI.class);
	LugaresForm lugares;
	HotelesForm hoteles;
	RestaurantesForm restaurantes;
	
	@Autowired
	public VaadinUI(LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.menubar = new MenuBar();
		lugares = new LugaresForm(this,repoLugar, lugarEditor);
		hoteles = new HotelesForm(this);
		restaurantes = new RestaurantesForm(this);
		
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
		// build layout
		
		img_inicio.setSource(getImageResource("inicio.jpg"));
		VerticalLayout ini = new VerticalLayout();
		ini.addComponents(menubarLayout, img_inicio);
		setContent(ini);

		// Define a common menu command for all the menu items.
		MenuBar.Command commandVerPanelLugar = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				ini.addComponent(lugares);
				lugares.setVisible(true);
				img_inicio.setVisible(false);
			}
		};
		
		MenuBar.Command commandVerPanelRestaurante = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				ini.addComponent(restaurantes);
				restaurantes.setVisible(true);
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
		menubar.addItem("Restaurantes", null, commandVerPanelRestaurante);
		menubar.addItem("Lugares", null, commandVerPanelLugar);

		

	}

	private final Command menuCommand = selectedItem -> selectedItem.getText();

	
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
