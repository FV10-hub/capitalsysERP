package py.com.capitalsys.capitalsysservices.services.impl.base;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsParametroRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsParametro;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsParametroServiceImpl 
extends CommonServiceImpl<BsParametro, BsParametroRepository> implements BsParametroService   {

	@Override
	public BsParametro buscarParametro(Long paramId, Long empresaId, Long moduloId) {
		return this.repository.buscarParametro(paramId, empresaId, moduloId);
	}

}
