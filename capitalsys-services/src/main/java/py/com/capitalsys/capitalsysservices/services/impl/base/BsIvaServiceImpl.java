/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsIvaRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsIva;
import py.com.capitalsys.capitalsysservices.services.base.BsIvaService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/**
 * 
 */
@Service
public class BsIvaServiceImpl 
extends CommonServiceImpl<BsIva, BsIvaRepository> implements BsIvaService {

	@Override
	public Page<BsIva> buscarTodos(Pageable pageable) {
		return repository.buscarTodos(pageable);
	}

	@Override
	public List<BsIva> buscarTodosLista() {
		return repository.buscarTodosLista();
	}

	@Override
	public List<BsIva> buscarIvaActivosLista() {
		return repository.buscarIvaActivosLista();
	}

}
