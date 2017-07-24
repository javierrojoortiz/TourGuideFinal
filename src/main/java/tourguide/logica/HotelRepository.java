package tourguide.logica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	List<Hotel> findByNombreStartsWithIgnoreCase(String nombre);
}
