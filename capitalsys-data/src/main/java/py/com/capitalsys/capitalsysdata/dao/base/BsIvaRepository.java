/**
 * 
 */
package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsIva;

/**
 * 
 */
public interface BsIvaRepository extends PagingAndSortingRepository<BsIva, Long> {
	
	@Query("SELECT m FROM BsIva m")
	Page<BsIva> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsIva m")
	List<BsIva> buscarTodosLista();
	
	@Query("SELECT m FROM BsIva m where m.estado = 'ACTIVO'")
	List<BsIva> buscarIvaActivosLista();

}
