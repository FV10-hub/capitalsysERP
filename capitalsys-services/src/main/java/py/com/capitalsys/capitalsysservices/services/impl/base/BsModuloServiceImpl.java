/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsMenuRepository;
import py.com.capitalsys.capitalsysdata.dao.base.BsModuloRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysservices.services.base.BsMenuService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;

/**
 * 
 */
@Service
public class BsModuloServiceImpl implements BsModuloService {
	
	@Autowired
	private BsModuloRepository bsModuloRepositoryImpl;

	@Override
	public Page<BsModulo> listarTodos(Pageable pageable) {
		Page<BsModulo> page =  this.bsModuloRepositoryImpl.buscarTodos(pageable);
		return page;
	}

	@Override
	public List<BsModulo> buscarTodosLista() {
		return bsModuloRepositoryImpl.buscarTodosLista();
	}

	@Override
	public BsModulo guardar(BsModulo obj) {
		return this.bsModuloRepositoryImpl.save(obj);
	}

	@Override
	public void eliminar(Long id) {
		this.bsModuloRepositoryImpl.deleteById(id);
	}

	@Override
	public List<BsModulo> buscarModulosActivosLista() {
		return this.bsModuloRepositoryImpl.buscarModulosActivosLista();
	}

}
