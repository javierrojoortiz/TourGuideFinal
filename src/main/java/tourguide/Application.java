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
			// save a couple of customers
			lugarRepository.save(new Lugar("La Catedral", "Edificio religioso"));
			lugarRepository.save(new Lugar("Iglesia del Santísimo Cristo", "Edificio religioso"));
			lugarRepository.save(new Lugar("Iglesia del Sagrado Corazón", "Edificio religioso"));
			lugarRepository.save(new Lugar("Iglesia de la Consolación", "Edificio religioso"));
			lugarRepository.save(new Lugar("Iglesia de Santa Lucía", "Edificio religioso"));
			lugarRepository.save(new Lugar("Ermita de la Virgen del Mar", "Edificio religioso"));
			lugarRepository.save(new Lugar("Iglesia de la Anunciación (La Compañía)", "Edificio religioso"));
			
			lugarRepository.save(new Lugar("Palacio de La Magdalena", "Edificio singular"));
			lugarRepository.save(new Lugar("Centro Botín", "Edificio singular"));
			lugarRepository.save(new Lugar("Palacio de Festivales", "Edificio singular"));
			lugarRepository.save(new Lugar("Biblioteca de Menéndez Pelayo", "Edificio singular"));
			lugarRepository.save(new Lugar("Gran Casino", "Edificio singular"));
			lugarRepository.save(new Lugar("Mercado de La Esperanza", "Edificio singular"));
			
			lugarRepository.save(new Lugar("Museo de Prehistoria y Arqueología de Cantabria", "Museo - Galería"));
			lugarRepository.save(new Lugar("Biblioteca de Menéndez Pelayo", "Museo - Galería"));
			lugarRepository.save(new Lugar("Casa – Museo de Menéndez Pelayo", "Museo - Galería"));
			lugarRepository.save(new Lugar("Museo de Arte Moderno y Contemporáneo de Santander y Cantabria", "Museo - Galería"));
			lugarRepository.save(new Lugar("Museo Marítimo del Cantábrico", "Museo - Galería"));
					
			lugarRepository.save(new Lugar("Los Peligros", "Playas"));
			lugarRepository.save(new Lugar("La Magdalena", "Playas"));
			lugarRepository.save(new Lugar("Bikinis", "Playas"));
			lugarRepository.save(new Lugar("El Camello", "Playas"));
			lugarRepository.save(new Lugar("La Concha", "Playas"));
			lugarRepository.save(new Lugar("El Sardinero", "Playas"));
			

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
	public CommandLineRunner loadDataRestaurante(RestauranteRepository restauranteRepository) {
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
			log.info("Lugar found with findByTipoStartsWithIgnoreCase('Edificio religioso'):");
			log.info("--------------------------------------------");
			for (Restaurante restauranteItaliano: restauranteRepository.findByTipoStartsWithIgnoreCase("Italiana")) {
				log.info(restauranteItaliano.toString());
			}
			log.info("");
		};
	}
	
	

}
