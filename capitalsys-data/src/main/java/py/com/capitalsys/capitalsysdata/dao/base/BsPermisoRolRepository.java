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
import py.com.capitalsys.capitalsysentities.entities.base.BsPermisoRol;

/**
 * 
 */
public interface BsPermisoRolRepository extends PagingAndSortingRepository<BsPermisoRol, Long> {
	
	@Query("SELECT m FROM BsPermisoRol m")
	Page<BsPermisoRol> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsPermisoRol m LEFT JOIN FETCH m.rol r LEFT JOIN FETCH m.bsMenu me ")
	List<BsPermisoRol> buscarTodosLista();
	
}
