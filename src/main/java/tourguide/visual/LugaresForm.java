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
import javax.servlet.annotation.WebServlet;

import org.springframework.util.StringUtils;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.InfoWindowClosedListener;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
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
	private ExternalResource externalResource;
	private String urlImg = "";
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinUI.class, widgetset = "")

	public static class Servlet extends VaadinServlet {
	}
	
	public LugaresForm(VaadinUI vaadinUI, LugarRepository repoLugar, LugarEditor lugarEditor) {
		this.vaadinUI = vaadinUI;
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.gridLugar = new Grid<>(Lugar.class);
		this.filter = new TextField("Filtro: ");

		Panel panelLugar = new Panel("Panel Lugar");

		HorizontalLayout actions = new HorizontalLayout(filter);
		HorizontalLayout mainLayout = new HorizontalLayout(panelLugar);
		HorizontalLayout secundaryLayout2 = new HorizontalLayout();
		setParent(mainLayout);

		panelLugar.addStyleName("mypanelexample");
		panelLugar.setSizeUndefined(); // Shrink to fit content
		// mainLayout.addComponent(panelLugar);
		panelLugar.setVisible(false);

		this.addComponent(actions);

		this.addStyleName("mipanel de contenidos");
		secundaryLayout2.addComponent(gridLugar);
		this.setSizeUndefined(); // Shrink to fit
		this.setMargin(true);
		lugarEditor.setSizeFull();
		secundaryLayout2.addComponent(lugarEditor);

		panelLugar.setContent(this);

		gridLugar.setWidth(700, Unit.PIXELS);
		gridLugar.setHeight(600, Unit.PIXELS);
		gridLugar.setHeightMode(HeightMode.ROW);
		gridLugar.setColumns("id", "nombreLugar", "tipo");

		filter.setPlaceholder("Filtrar por tipo");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listLugares(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		gridLugar.asSingleSelect().addValueChangeListener(e -> {
			lugarEditor.editLugar(e.getValue());
			lugarEditor.setVisible(isVisible());
		});
		mainLayout.addComponents(secundaryLayout2);
		this.addComponents(secundaryLayout2);

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

	private Image setWeblImage(String urlImg, Image imagenL) {

		ExternalResource externalResource = new ExternalResource(urlImg);

		imagenL.setSource(externalResource);
		imagenL.setWidth("500px");
		imagenL.setHeight("500px");
		return imagenL;
	}

}
