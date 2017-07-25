package tourguide.visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;

@SuppressWarnings({ "unused", "serial" })
public class LugaresForm extends FormLayout {

	private final LugarRepository repoLugar;

	private final LugarEditor lugarEditor;

	final Grid<Lugar> gridLugar;

	final TextField filter;

	private VaadinUI vaadinUI;
	final ExternalResource externalResource;
	private Image imagenWeb = new Image();

	public LugaresForm(VaadinUI vaadinUI, LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.vaadinUI = vaadinUI;
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.gridLugar = new Grid<>(Lugar.class);
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
		secundaryLayout1.addComponent(lugarEditor);
		String urlImg="https://santanderspain.info/wp-content/uploads/2014/08/Catedral-de-Santander-686x1030.jpg";
		
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

		// Initialize listing
		listLugares(null);

	}

	// private final Command menuCommand = selectedItem -> selectedItem.getText();

	// tag::listLugares[]
	void listLugares(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			gridLugar.setItems(repoLugar.findAll());
		} else {
			gridLugar.setItems(repoLugar.findByTipoStartsWithIgnoreCase(filterText));
		}
	}

}
