/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;

/**
 * 
 */
public interface BsPersonaService {

	Page<BsPersona> listarTodos(Pageable pageable);
	
	List<BsPersona> buscarTodosLista();
	
	BsPersona guardar(BsPersona obj);
	
	void eliminar(Long id);
}