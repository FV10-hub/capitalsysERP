package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsTimbradoRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;
import py.com.capitalsys.capitalsysservices.services.base.BsTimbradoService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 2 ene. 2024 - Elitebook
*/
@Service
public class BsTimbradoServiceImpl 
extends CommonServiceImpl<BsTimbrado, BsTimbradoRepository> implements BsTimbradoService {

	@Override
	public List<BsTimbrado> buscarBsTimbradoActivosLista(Long idEmpresa) {
		return this.repository.buscarBsTimbradoActivosLista(idEmpresa);
	}

}
