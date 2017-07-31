package tourguide.visual;

import java.io.File;

import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringUI;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import tourguide.logica.HotelEditor;
import tourguide.logica.HotelRepository;
import tourguide.logica.Lugar;
import tourguide.logica.LugarEditor;
import tourguide.logica.LugarRepository;
import tourguide.logica.RestauranteEditor;
import tourguide.logica.RestauranteRepository;





@SpringUI
public class VaadinUI extends UI {

	private final Image img_inicio = new Image();
	
	MenuBar menubar;
	
	private final LugarRepository repoLugar;
	private final LugarEditor lugarEditor;
	
	private final RestauranteEditor restauranteEditor;
	private final RestauranteRepository repoRestaurante;
	
	private final HotelRepository repoHotel;
	private final HotelEditor hotelEditor;

	Logger logger = Logger.getLogger(VaadinUI.class);
	LugaresForm lugares;
	HotelesForm hoteles;
	RestaurantesForm restaurantes;
	
	@Autowired
	public VaadinUI(LugarRepository repoLugar, LugarEditor lugarEditor,
			RestauranteEditor restauranteEditor, RestauranteRepository repoRestaurante,
			HotelEditor hotelEditor, HotelRepository repoHotel) {
		this.repoRestaurante = repoRestaurante;
		this.restauranteEditor = restauranteEditor;
		this.repoLugar = repoLugar;
		this.lugarEditor = lugarEditor;
		this.hotelEditor = hotelEditor;
		this.repoHotel = repoHotel;
		this.menubar = new MenuBar();
		
		lugares = new LugaresForm(this,repoLugar, lugarEditor);
		hoteles = new HotelesForm(this,repoHotel, hotelEditor);
		restaurantes = new RestaurantesForm(this, repoRestaurante, restauranteEditor);
		
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
				restaurantes.setVisible(false);
				hoteles.setVisible(false);
			}
		};
		
		MenuBar.Command commandVerPanelRestaurante = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				ini.addComponent(restaurantes);
				restaurantes.setVisible(true);
				img_inicio.setVisible(false);
				lugares.setVisible(false);
				hoteles.setVisible(false);
				
			}
		};
		
		MenuBar.Command commandInicio = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				img_inicio.setVisible(true);
				lugares.setVisible(false);
				hoteles.setVisible(false);
				restaurantes.setVisible(false);
			}
		};
		MenuBar.Command commandHotel = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				ini.addComponent(hoteles);
				img_inicio.setVisible(false);
				lugares.setVisible(false);
				restaurantes.setVisible(false);
				hoteles.setVisible(true);
			}
		};

		menubar.setWidth(100.0f, Unit.PERCENTAGE);
		menubar.addItem("Inicio", VaadinIcons.HOME , commandInicio);
		menubar.addItem("Hoteles", null, commandHotel);
		menubar.addItem("Restaurantes", null, commandVerPanelRestaurante);
		menubar.addItem("Lugares", null, commandVerPanelLugar);
	

	}

	
	private Resource getImageResource(String recurso) {

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		logger.debug("Ruta: " + basepath);
		FileResource resource = new FileResource(new File (basepath + "/images/" + recurso));
		return resource;
	}

}

