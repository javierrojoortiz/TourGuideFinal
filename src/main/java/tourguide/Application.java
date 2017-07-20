package tourguide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tourguide.logica.Customer;
import tourguide.logica.CustomerRepository;
import tourguide.logica.Lugar;
import tourguide.logica.LugarRepository;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(LugarRepository lugarRepository) {
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
			log.info("Customers found with findAll():");
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
			log.info("Customer found with findByTipoStartsWithIgnoreCase('Edificio religioso'):");
			log.info("--------------------------------------------");
			for (Lugar edificioReligioso : lugarRepository
					.findByTipoStartsWithIgnoreCase("Edificio religioso")) {
				log.info(edificioReligioso.toString());
			}
			log.info("");
		};
	}
	
//	@Bean
//	public CommandLineRunner loadData(CustomerRepository repository) {
//		return (args) -> {
//			// save a couple of customers
//			repository.save(new Customer("Jack", "Bauer"));
//			repository.save(new Customer("Chloe", "O'Brian"));
//			repository.save(new Customer("Kim", "Bauer"));
//			repository.save(new Customer("David", "Palmer"));
//			repository.save(new Customer("Michelle", "Dessler"));
//
//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			for (Customer customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Customer customer = repository.findOne(1L);
//			log.info("Customer found with findOne(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
//			log.info("--------------------------------------------");
//			for (Customer bauer : repository
//					.findByLastNameStartsWithIgnoreCase("Bauer")) {
//				log.info(bauer.toString());
//			}
//			log.info("");
//		};
//	}
	

}
