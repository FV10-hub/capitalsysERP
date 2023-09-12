/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysentities.entities.base.BsPermisoRol;
import py.com.capitalsys.capitalsysentities.entities.base.BsRol;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/**
 * 
 */
public interface BsPermisoRolService  extends CommonService<BsPermisoRol> {

	Page<BsPermisoRol> listarTodos(Pageable pageable);
	
	List<BsPermisoRol> buscarTodosLista();
	
}