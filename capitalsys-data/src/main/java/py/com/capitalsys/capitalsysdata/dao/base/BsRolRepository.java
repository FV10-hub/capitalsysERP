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
import py.com.capitalsys.capitalsysentities.entities.base.BsRol;

/**
 * 
 */
public interface BsRolRepository extends PagingAndSortingRepository<BsRol, Long> {
	
	@Query("SELECT m FROM BsRol m")
	Page<BsRol> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsRol m")
	List<BsRol> buscarTodosLista();

}
