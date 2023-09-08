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

/**
 * 
 */
public interface BsMenuRepository extends PagingAndSortingRepository<BsMenu, Long> {
	
	@Query("SELECT m FROM BsMenu m JOIN m.bsModulo l")
	Page<BsMenu> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsMenu m LEFT JOIN FETCH m.bsModulo mo")
	List<BsMenu> buscarTodosLista();
	
	@Query("SELECT m FROM BsMenu m WHERE m.bsModulo.id = ?1")
	List<BsMenu> buscarMenuPorModuloLista(Long id);

}
