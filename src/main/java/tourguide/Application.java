package tourguide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tourguide.logica.*;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
		

	@Bean
	public CommandLineRunner loadDataLugares(LugarRepository lugarRepository) {
		return (args) -> {
			//hotelRepository.save(new Hotel("Hotel Real",1.24,"https://santanderspain.info/wp-content/uploads/2014/08/Catedral-de-Santander-686x1030.jpg",
			//		"Perez Galdos",5,4,"Ejempo",43.460699,-3.80746));
			
			
			lugarRepository.save(new Lugar("La Catedral", "Edificio religioso",
					"https://santanderspain.info/wp-content/uploads/2014/08/Catedral-de-Santander-686x1030.jpg",
					"Avda me la invento NA" , "942 20 21 22", "10:00 - 20:00", false ,
					"Una catedral muy bonita lalalala y demas ",43.460699,-3.80746));
			lugarRepository.save(new Lugar("Iglesia del Santísimo Cristo", "Edificio religioso",
					"https://santanderspain.info/wp-content/uploads/2014/08/Iglesia-del-Cristo-1030x686.jpg",
					"C/ Menendez Pelayo 21", "942 20 21 22", "09:30 - 14:00", true,
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
					+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
					+ " nisi ut aliquip ex ea commodo consequat.",43.460568,-3.806838));
			lugarRepository.save(new Lugar("Iglesia del Sagrado Corazón", "Edificio religioso",
					"https://santanderspain.info/wp-content/uploads/2014/08/Iglesia-Sagrado-Coraz%C3%B3n.jpg",
					"C/Juan Luis de Espinoza 44","942 20 21 22","10:30 - 15:00",false,
					"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod "
					+ "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
					+ " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",43.463294,-3.805153));
//			lugarRepository.save(new Lugar("Iglesia de la Consolación", "Edificio religioso",null));
//			lugarRepository.save(new Lugar("Iglesia de Santa Lucía", "Edificio religioso"));
//			lugarRepository.save(new Lugar("Ermita de la Virgen del Mar", "Edificio religioso"));
//			lugarRepository.save(new Lugar("Iglesia de la Anunciación (La Compañía)", "Edificio religioso"));
//			
//			lugarRepository.save(new Lugar("Palacio de La Magdalena", "Edificio singular"));
//			lugarRepository.save(new Lugar("Centro Botín", "Edificio singular"));
//			lugarRepository.save(new Lugar("Palacio de Festivales", "Edificio singular"));
//			lugarRepository.save(new Lugar("Biblioteca de Menéndez Pelayo", "Edificio singular"));
//			lugarRepository.save(new Lugar("Gran Casino", "Edificio singular"));
//			lugarRepository.save(new Lugar("Mercado de La Esperanza", "Edificio singular"));
//			
//			lugarRepository.save(new Lugar("Museo de Prehistoria y Arqueología de Cantabria", "Museo - Galería"));
//			lugarRepository.save(new Lugar("Biblioteca de Menéndez Pelayo", "Museo - Galería"));
//			lugarRepository.save(new Lugar("Casa – Museo de Menéndez Pelayo", "Museo - Galería"));
//			lugarRepository.save(new Lugar("Museo de Arte Moderno y Contemporáneo de Santander y Cantabria", "Museo - Galería"));
//			lugarRepository.save(new Lugar("Museo Marítimo del Cantábrico", "Museo - Galería"));
//					
			lugarRepository.save(new Lugar("Los Peligros", "Playas","https://santanderspain.info/wp-content/uploads/2014/08/IMG_60481-687x1030.jpg", "Avda Aventura" , "942 28 21 25", "Sin horarios", false ,
					"Una playa peligrosa para el baño",43.466559,-3.777167));
//			lugarRepository.save(new Lugar("La Magdalena", "Playas"));
//			lugarRepository.save(new Lugar("Bikinis", "Playas"));
//			lugarRepository.save(new Lugar("El Camello", "Playas"));
//			lugarRepository.save(new Lugar("La Concha", "Playas"));
//			lugarRepository.save(new Lugar("El Sardinero", "Playas"));
			

			// fetch all lugares
			log.info("Lugares found with findAll():");
			log.info("-------------------------------");
			for (Lugar lugar : lugarRepository.findAll()) {
				log.info(lugar.toString());
			}
			log.info("");

			// fetch an individual lugar by ID
			Lugar lugar = lugarRepository.findOne(1L);
			log.info("Lugar found with findOne(1L):");
			log.info("--------------------------------");
			log.info(lugar.toString());
			log.info("");

			// fetch lugares by last name
			log.info("Lugar found with findByTipoStartsWithIgnoreCase('Edificio religioso'):");
			log.info("--------------------------------------------");
			for (Lugar edificioReligioso : lugarRepository
					.findByTipoStartsWithIgnoreCase("Edificio religioso")) {
				log.info(edificioReligioso.toString());
			}
			log.info("");
		};
	}
	
	@Bean
	public CommandLineRunner loadDataRestaurantes(RestauranteRepository restauranteRepository) {
		return (args) -> {
			// save a couple of customers
			restauranteRepository.save(new Restaurante("La Vinoteca", "Fusión"));
			restauranteRepository.save(new Restaurante("Posada del Mar", "Marisco"));
			restauranteRepository.save(new Restaurante("La Bombi", "Mediterranea"));
			restauranteRepository.save(new Restaurante("El Serbal", "Mediterranea"));
			restauranteRepository.save(new Restaurante("Pizzeria Vittorio", "Italiana"));
			restauranteRepository.save(new Restaurante("Il Bocone", "Italiana"));
			restauranteRepository.save(new Restaurante("Cocolatería Áliva", "Café"));
			
			/*restauranteRepository.save(new Restaurante("Palacio de La Magdalena", "Edificio singular"));
			restauranteRepository.save(new Restaurante("Centro Botín", "Edificio singular"));
			restauranteRepository.save(new Restaurante("Palacio de Festivales", "Edificio singular"));
			restauranteRepository.save(new Restaurante("Biblioteca de Menéndez Pelayo", "Edificio singular"));
			restauranteRepository.save(new Restaurante("Gran Casino", "Edificio singular"));
			restauranteRepository.save(new Restaurante("Mercado de La Esperanza", "Edificio singular"));
			
			restauranteRepository.save(new Restaurante("Museo de Prehistoria y Arqueología de Cantabria", "Museo - Galería"));
			restauranteRepository.save(new Restaurante("Biblioteca de Menéndez Pelayo", "Museo - Galería"));
			restauranteRepository.save(new Restaurante("Casa – Museo de Menéndez Pelayo", "Museo - Galería"));
			restauranteRepository.save(new Restaurante("Museo de Arte Moderno y Contemporáneo de Santander y Cantabria", "Museo - Galería"));
			restauranteRepository.save(new Restaurante("Museo Marítimo del Cantábrico", "Museo - Galería"));
					
			restauranteRepository.save(new Restaurante("Los Peligros", "Playas"));
			restauranteRepository.save(new Restaurante("La Magdalena", "Playas"));
			restauranteRepository.save(new Restaurante("Bikinis", "Playas"));
			restauranteRepository.save(new Restaurante("El Camello", "Playas"));
			restauranteRepository.save(new Restaurante("La Concha", "Playas"));
			restauranteRepository.save(new Restaurante("El Sardinero", "Playas"));
			*/

			// fetch all lugares
			log.info("Lugares found with findAll():");
			log.info("-------------------------------");
			for (Restaurante restaurante : restauranteRepository.findAll()) {
				log.info(restaurante.toString());
			}
			log.info("");

			// fetch an individual lugar by ID
			Restaurante restaurante = restauranteRepository.findOne(1L);
			log.info("Lugar found with findOne(1L):");
			log.info("--------------------------------");
			log.info(restaurante.toString());
			log.info("");

			// fetch lugares by last name
			log.info("Lugar found with findByTipoStartsWithIgnoreCase('Italiana'):");
			log.info("--------------------------------------------");
			for (Restaurante restauranteItaliano: restauranteRepository.findByNombreStartsWithIgnoreCase("Italiana")) {
				log.info(restauranteItaliano.toString());
			}
			log.info("");
			
		};
	}
	@Bean
	public CommandLineRunner loadDataHoteles(HotelRepository hotelRepository) {
		return (args) -> {
			hotelRepository.save(new Hotel("Hotel Real",1.24,"https://s-ec.bstatic.com/images/hotel/max1024x768/100/100900180.jpg",
				"Perez Galdos",5,4,"Ejempo",43.460699,-3.80746));
			hotelRepository.save(new Hotel("Hotel Bahia", 2.05,"https://media-cdn.tripadvisor.com/media/photo-s/09/01/70/24/hotel-bahia-santander.jpg",
					"Plaza de las Cachavas",4,4,"En la zona de PuertoChico",22.462,-3.50));


			// fetch all lugares
			log.info("Lugares found with findAll():");
			log.info("-------------------------------");
			for (Hotel hotel : hotelRepository.findAll()) {
				log.info(hotel.toString());
			}
			log.info("");

			// fetch an individual lugar by ID
			Hotel hotel = hotelRepository.findOne(1L);
			log.info("Lugar found with findOne(1L):");
			log.info("--------------------------------");
			log.info(hotel.toString());
			log.info("");

			
		};
	}
	
	

}
