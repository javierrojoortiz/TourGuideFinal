package tourguide.visual;

import java.io.File;

import org.springframework.util.StringUtils;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;


@SuppressWarnings({ "unused", "serial" })
public class LugaresForm extends FormLayout  {


	private final LugarRepository repoLugar;

	
	private final LugarEditor lugarEditor;

	final Grid<Lugar> gridLugar;

	final TextField filter;
	
	private VaadinUI vaadinUI;
	
	public LugaresForm(VaadinUI vaadinUI, LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.vaadinUI = vaadinUI;
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.gridLugar = new Grid<>(Lugar.class);
		this.filter = new TextField("Filtro: ");
		
		Panel panelLugar = new Panel("Panel Lugar");
		
		HorizontalLayout actions = new HorizontalLayout(filter);
		VerticalLayout mainLayout = new VerticalLayout(panelLugar);
		setParent(mainLayout);
		
		panelLugar.addStyleName("mypanelexample");
		panelLugar.setSizeUndefined(); // Shrink to fit content
		mainLayout.addComponent(panelLugar);
		panelLugar.setVisible(false);
		
		this.addComponent(actions);
		this.addStyleName("mipanel de contenidos");
		this.addComponent(gridLugar);
		this.setSizeUndefined(); // Shrink to fit
		this.setMargin(true);
		this.addComponent(lugarEditor);
		panelLugar.setContent(this);
		
		gridLugar.setWidth(700, Unit.PIXELS);
		gridLugar.setHeight(300, Unit.PIXELS);
		gridLugar.setColumns("id", "nombreLugar", "tipo");
		

		filter.setPlaceholder("Filtrar por tipo");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		//filter.addValueChangeListener(e -> listLugares(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		gridLugar.asSingleSelect().addValueChangeListener(e -> {
			lugarEditor.editLugar(e.getValue());
		});

		// Listen changes made by the editor, refresh data from backend
		lugarEditor.setChangeHandler(() -> {
			lugarEditor.setVisible(false);
			listLugares(filter.getValue());
		});

		// Initialize listing
		listLugares(null);
	}	

	// tag::listLugares[]
		void listLugares(String filterText) {
			if (StringUtils.isEmpty(filterText)) {
				gridLugar.setItems(repoLugar.findAll());
			} else {
				gridLugar.setItems(repoLugar.findByTipoStartsWithIgnoreCase(filterText));
			}
		}

}


