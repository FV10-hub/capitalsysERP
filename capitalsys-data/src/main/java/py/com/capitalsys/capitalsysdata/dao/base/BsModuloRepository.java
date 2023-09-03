/**
 * 
 */
package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;

/**
 * 
 */
public interface BsModuloRepository extends PagingAndSortingRepository<BsModulo, Long> {
	
	@Query("SELECT m FROM BsModulo m")
	Page<BsModulo> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsModulo m")
	List<BsModulo> buscarTodosLista();

}
