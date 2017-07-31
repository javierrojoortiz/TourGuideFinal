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

import tourguide.logica.Hotel;
import tourguide.logica.HotelEditor;
import tourguide.logica.HotelRepository;
import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;

@SuppressWarnings({ "unused", "serial" })
public class HotelesForm extends FormLayout {

	private final HotelRepository repoHotel;

	private final HotelEditor hotelEditor;

	final Grid<Hotel> gridHotel;

	final TextField filter;
	
	private VaadinUI vaadinUI;
	private ExternalResource externalResource;
	private String urlImg = "";
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinUI.class, widgetset = "")

	public static class Servlet extends VaadinServlet {
	}
	
	public HotelesForm(VaadinUI vaadinUI, HotelRepository repoHotel, HotelEditor hotelEditor) {
		this.vaadinUI = vaadinUI;
		this.repoHotel = repoHotel;
		this.hotelEditor = hotelEditor;
		this.gridHotel = new Grid<>(Hotel.class);
		this.filter = new TextField("Filtro: ");

		Panel panelHotel = new Panel("Panel Hoteles");

		HorizontalLayout actions = new HorizontalLayout(filter);
		HorizontalLayout mainLayout = new HorizontalLayout(panelHotel);
		HorizontalLayout secundaryLayout2 = new HorizontalLayout();
		setParent(mainLayout);

		panelHotel.addStyleName("mypanelexample");
		panelHotel.setSizeUndefined(); // Shrink to fit content
		// mainLayout.addComponent(panelLugar);
		panelHotel.setVisible(false);

		this.addComponent(actions);

		this.addStyleName("mipanel de contenidos");
		secundaryLayout2.addComponent(gridHotel);
		this.setSizeUndefined(); // Shrink to fit
		this.setMargin(true);
		hotelEditor.setSizeFull();
		secundaryLayout2.addComponent(hotelEditor);

		panelHotel.setContent(this);

		gridHotel.setWidth(700, Unit.PIXELS);
		gridHotel.setHeight(600, Unit.PIXELS);
		gridHotel.setHeightMode(HeightMode.ROW);
		gridHotel.setColumns("nombre", "estrellas" ,"km_playa");

		filter.setPlaceholder("Filtrar por nombre");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listHoteles(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		gridHotel.asSingleSelect().addValueChangeListener(e -> {
			hotelEditor.editHotel(e.getValue());
			hotelEditor.setVisible(isVisible());
		});
		mainLayout.addComponents(secundaryLayout2);
		this.addComponents(secundaryLayout2);

		// Initialize listing
		listHoteles(null);

	}

	// private final Command menuCommand = selectedItem -> selectedItem.getText();

	// tag::listLugares[]

	void listHoteles(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			gridHotel.setItems(repoHotel.findAll());
		} else {
			gridHotel.setItems(repoHotel.findByNombreStartsWithIgnoreCase(filterText));
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
