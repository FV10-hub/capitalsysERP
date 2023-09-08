/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;

/**
 * 
 */
public interface BsMenuItemService {
	
	List<BsMenuItem> findMenuAgrupado(Long idModulo);
	List<BsMenuItem> findMenuItemAgrupado(Long idMenuItemAgrupador);
}