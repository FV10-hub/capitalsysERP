package py.com.capitalsys.capitalsysdata.dao.stock;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;

public interface StoArticuloRepository extends PagingAndSortingRepository<StoArticulo, Long> {
	
	@Query("SELECT m FROM StoArticulo m")
	Page<StoArticulo> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM StoArticulo m")
	List<StoArticulo> buscarTodosLista();
	
	@Query("SELECT m FROM StoArticulo m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<StoArticulo> buscarStoArticuloActivosLista(Long idEmpresa);
	
	
}
