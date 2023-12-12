/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.base.BsIva;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/**
 * 
 */
public interface BsIvaService extends CommonService<BsIva>  {
	
	Page<BsIva> buscarTodos(Pageable pageable);
	
	List<BsIva> buscarTodosLista();
	
	List<BsIva> buscarIvaActivosLista();
	
}