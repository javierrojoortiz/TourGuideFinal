package tourguide.visual;

import org.springframework.util.StringUtils;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.Restaurante;
import tourguide.logica.RestauranteEditor;
import tourguide.logica.RestauranteRepository;

@SuppressWarnings({ "unused", "serial" })
public class RestaurantesForm extends FormLayout{

	private final RestauranteRepository repoRestaurante;

	private final RestauranteEditor restauranteEditor;

	final Grid<Restaurante> gridLugar;

	final TextField filter;

	private VaadinUI vaadinUI;
	final ExternalResource externalResource;
	private Image imagenWeb = new Image();

	public RestaurantesForm(VaadinUI vaadinUI, RestauranteRepository repoRestaurante, RestauranteEditor restauranteEditor) {
		this.vaadinUI = vaadinUI;
		this.repoRestaurante = repoRestaurante;
		this.restauranteEditor = restauranteEditor;
		this.gridLugar = new Grid<>(Restaurante.class);
		this.filter = new TextField("Filtro: ");

		Panel panelLugar = new Panel("Panel Lugar");

		HorizontalLayout actions = new HorizontalLayout(filter);
		HorizontalLayout mainLayout = new HorizontalLayout(panelLugar);
		VerticalLayout secundaryLayout1 = new VerticalLayout();
		HorizontalLayout secundaryLayout2 = new HorizontalLayout(secundaryLayout1,imagenWeb);
		setParent(mainLayout);

		panelLugar.addStyleName("mypanelexample");
		panelLugar.setSizeUndefined(); // Shrink to fit content
		// mainLayout.addComponent(panelLugar);
		panelLugar.setVisible(false);

		this.addComponent(actions);


		this.addStyleName("mipanel de contenidos");
		secundaryLayout1.addComponent(gridLugar);
		this.setSizeUndefined(); // Shrink to fit
		this.setMargin(true);
		secundaryLayout1.addComponent(restauranteEditor);
		String urlImg="https://www.tripadvisor.es/Restaurant_Review-g187484-d3686019-Reviews-La_Vinoteca-Santander_Cantabria.html#photos;"
				+ "geo=187484&detail=3686019&ff=58067474&albumViewMode=hero&aggregationId=101&albumid=101&baseMediaId=58067474&"
				+ "thumbnailMinWidth=50&cnt=30&offset=-1&filter=7&autoplay=";
		
		externalResource = new ExternalResource(urlImg);

		imagenWeb.setSource(externalResource);
		imagenWeb.setWidth("500px");
		imagenWeb.setHeight("500px");
		
		secundaryLayout2.addComponent(imagenWeb);

		mainLayout.addComponents(secundaryLayout2);
		this.addComponents(secundaryLayout2);

		panelLugar.setContent(this);

		gridLugar.setWidth(700, Unit.PIXELS);
		gridLugar.setHeight(300, Unit.PIXELS);
		gridLugar.setHeightMode( HeightMode.ROW );
		gridLugar.setColumns("nombre", "tipo");

		filter.setPlaceholder("Filtrar por tipo");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listLugares(e.getValue()));


		// Connect selected Customer to editor or hide if none is selected
		gridLugar.asSingleSelect().addValueChangeListener(e -> {
			restauranteEditor.editRestaurante(e.getValue());
		});
		
		
		// Initialize listing
		listLugares(null);

	}

	// private final Command menuCommand = selectedItem -> selectedItem.getText();

	// tag::listLugares[]
	void listLugares(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			gridLugar.setItems(repoRestaurante.findAll());
		} else {
			gridLugar.setItems(repoRestaurante.findByNombreStartsWithIgnoreCase(filterText));
		}
	}
}
