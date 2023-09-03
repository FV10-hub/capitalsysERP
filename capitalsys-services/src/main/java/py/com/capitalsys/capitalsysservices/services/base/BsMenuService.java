/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;

/**
 * 
 */
public interface BsMenuService {

	Page<BsMenu> listarTodos(Pageable pageable);
	
	List<BsMenu> buscarTodosLista();
}