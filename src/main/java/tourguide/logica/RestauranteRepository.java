package tourguide.logica;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	List<Restaurante> findByTipoStartsWithIgnoreCase(String tipo);
	//List<Restaurante> findByNombreStartsWithIgnoreCase(String nombre);
}
