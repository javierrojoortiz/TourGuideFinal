package tourguide.logica;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class RestauranteEditor extends VerticalLayout{


	private final RestauranteRepository restauranteRepository;

	/**
	 * The currently edited lugar
	 */
	private Restaurante restaurante;

	/* Fields to edit properties in lugar entity */
	TextField nombre = new TextField("Nombre del restaurante");
	TextField tipo = new TextField("Tipo");
	
	TextField imagenRecurso = new TextField("Imagen");
	
	TextField direccion = new TextField("Dirección: ");

	TextField telefono = new TextField("Teléfono: ");
		
	TextField horario = new TextField("Horario de apertura: ");
	
	CheckBox visitaGuiada= new CheckBox("¿Tiene visitas guiadas?");
	
	TextField descripcion = new TextField("Descripción");

	CssLayout actions = new CssLayout();

	Binder<Restaurante> binder = new Binder<>(Restaurante.class);
	
	private GoogleMap ubicacion = new GoogleMap(null, null,null);
	private GoogleMapMarker marcador;

	@Autowired
	public RestauranteEditor(RestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
		
		
	
		nombre.setMaxLength(300);
		nombre.setReadOnly(true);
		
		tipo.setEnabled(false);
		imagenRecurso.setEnabled(false);
		direccion.setEnabled(false);
		telefono.setEnabled(false);
		horario.setEnabled(false);
		visitaGuiada.setEnabled(false);
		descripcion.setEnabled(false);
		descripcion.setSizeFull();
		
		ubicacion.setEnabled(false);
		
		
		addComponents(nombre, tipo,imagenRecurso,direccion,telefono,horario,visitaGuiada,descripcion);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	
		setVisible(false);
	}
	public final void editRestaurante(Restaurante r) {
		if (r == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = r.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			restaurante = restauranteRepository.findOne(r.getId());
			
			ubicacion.setCenter(new LatLon(r.getLatitud(),r.getLongitud()));
			ubicacion.setZoom(18);
			ubicacion.setSizeFull();
			marcador = new GoogleMapMarker();
			marcador.setPosition(new LatLon(r.getLatitud(),r.getLongitud()));
			
		}
		else {
			restaurante = r;
		}
//		cancel.setVisible(persisted);
		
		
		binder.setBean(restaurante);

		setVisible(true);

		// A hack to ensure the whole form is visible
	//	save.focus();
		// Select all text in nombreLugar field automatically
		nombre.selectAll();
	}

	public interface ChangeHandler {

		void onChange();
	}


}