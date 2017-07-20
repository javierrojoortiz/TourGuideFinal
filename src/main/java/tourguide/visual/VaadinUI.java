package tourguide.visual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.Customer;
import tourguide.logica.CustomerEditor;
import tourguide.logica.CustomerRepository;
import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;

@SpringUI
public class VaadinUI extends UI {

	private final LugarRepository repoLugar;

	private final LugarEditor lugarEditor;

	final Grid<Lugar> grid;

	final TextField filter;

	private final Button addNewBtn;
	
	final MenuBar menubar;
	

	@Autowired
	public VaadinUI(LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.grid = new Grid<>(Lugar.class);
		this.menubar = new MenuBar();
		this.filter = new TextField();
		this.addNewBtn = new Button("Añadir Lugar", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(menubarLayout,actions, grid, lugarEditor);
		setContent(mainLayout);
		
		menubar.setWidth(100.0f, Unit.PERCENTAGE);
		menubar.addItem("Lugares", null, null);
		menubar.addItem("Restaurantes", null, null);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "nombreLugar", "tipo");

		filter.setPlaceholder("Filter by last name");

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
   

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repoLugar.findAll());
		}
		else {
			grid.setItems(repoLugar.findByTipoStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]

}










//@SpringUI
//public class VaadinUI extends UI {
//
//	private final CustomerRepository repo;
//
//	private final CustomerEditor editor;
//
//	final Grid<Customer> grid;
//
//	final TextField filter;
//
//	private final Button addNewBtn;
//	
//	final MenuBar menubar;
//	
//
//	@Autowired
//	public VaadinUI(CustomerRepository repo, CustomerEditor editor) {
//		this.repo = repo;
//		this.editor = editor;
//		this.grid = new Grid<>(Customer.class);
//		this.menubar = new MenuBar();
//		this.filter = new TextField();
//		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
//	}
//
//	@Override
//	protected void init(VaadinRequest request) {
//		HorizontalLayout menubarLayout = new HorizontalLayout(menubar);
//		// build layout
//		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
//		VerticalLayout mainLayout = new VerticalLayout(menubarLayout,actions, grid, editor);
//		setContent(mainLayout);
//		
//		menubar.setWidth(100.0f, Unit.PERCENTAGE);
//		menubar.addItem("Lugares", null, null);
//		menubar.addItem("Restaurantes", null, null);
//
//		grid.setHeight(300, Unit.PIXELS);
//		grid.setColumns("id", "firstName", "lastName");
//
//		filter.setPlaceholder("Filter by last name");
//
//		// Hook logic to components
//
//		// Replace listing with filtered content when user changes filter
//		filter.setValueChangeMode(ValueChangeMode.LAZY);
//		filter.addValueChangeListener(e -> listCustomers(e.getValue()));
//
//		// Connect selected Customer to editor or hide if none is selected
//		grid.asSingleSelect().addValueChangeListener(e -> {
//			editor.editCustomer(e.getValue());
//		});
//
//		// Instantiate and edit new Customer the new button is clicked
//		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
//
//		// Listen changes made by the editor, refresh data from backend
//		editor.setChangeHandler(() -> {
//			editor.setVisible(false);
//			listCustomers(filter.getValue());
//		});
//
//		// Initialize listing
//		listCustomers(null);
//	}
//	
//    private final Command menuCommand = selectedItem -> selectedItem.getText();
//   
//
//	// tag::listCustomers[]
//	void listCustomers(String filterText) {
//		if (StringUtils.isEmpty(filterText)) {
//			grid.setItems(repo.findAll());
//		}
//		else {
//			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
//		}
//	}
//	// end::listCustomers[]
//
//}
