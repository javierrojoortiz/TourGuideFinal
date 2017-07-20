package tourguide.logica;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LugarRepository extends JpaRepository<Lugar, Long> {

	List<Lugar> findByTipoStartsWithIgnoreCase(String tipo);
}
