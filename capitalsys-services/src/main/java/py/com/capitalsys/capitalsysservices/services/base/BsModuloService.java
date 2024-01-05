/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;

/**
 * 
 */
public interface BsModuloService {

	Page<BsModulo> listarTodos(Pageable pageable);
	
	List<BsModulo> buscarTodosLista();
	
	List<BsModulo> buscarModulosActivosLista();
	
	BsModulo findByCodigo(String codigo);
	
	BsModulo guardar(BsModulo obj);
	
	void eliminar(Long id);
}