package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsTipoValorRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysservices.services.base.BsTipoValorService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsTipoValorServiceImpl 
extends CommonServiceImpl<BsTipoValor, BsTipoValorRepository> implements BsTipoValorService   {

	@Override
	public List<BsTipoValor> buscarTipoValorActivosLista(Long idEmpresa) {
		return repository.buscarTipoValorActivosLista(idEmpresa);
	}

}
