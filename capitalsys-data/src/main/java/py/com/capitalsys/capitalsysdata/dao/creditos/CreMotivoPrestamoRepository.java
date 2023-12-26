package py.com.capitalsys.capitalsysdata.dao.creditos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreMotivoPrestamo;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreMotivoPrestamoRepository extends PagingAndSortingRepository<CreMotivoPrestamo, Long> {
	@Query("SELECT m FROM CreMotivoPrestamo m")
	Page<CreMotivoPrestamo> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CreMotivoPrestamo m")
	List<CreMotivoPrestamo> buscarTodosLista();
	
	@Query("SELECT m FROM CreMotivoPrestamo m where m.estado = 'ACTIVO'")
	List<CreMotivoPrestamo> buscarCreMotivoPrestamoActivosLista();
}
