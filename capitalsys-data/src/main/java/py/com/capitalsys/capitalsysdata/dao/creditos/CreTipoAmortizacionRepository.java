package py.com.capitalsys.capitalsysdata.dao.creditos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;


/*
* 26 dic. 2023 - Elitebook
*/
public interface CreTipoAmortizacionRepository extends PagingAndSortingRepository<CreTipoAmortizacion, Long> {
	@Query("SELECT m FROM CreTipoAmortizacion m")
	Page<CreTipoAmortizacion> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CreTipoAmortizacion m")
	List<CreTipoAmortizacion> buscarTodosLista();
	
	@Query("SELECT m FROM CreTipoAmortizacion m where m.estado = 'ACTIVO'")
	List<CreTipoAmortizacion> buscarCreTipoAmortizacionActivosLista();
}
