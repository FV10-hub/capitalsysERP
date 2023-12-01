package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobCobradorRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrador;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobCobradorService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class CobCobradorServiceImpl 
extends CommonServiceImpl<CobCobrador, CobCobradorRepository> implements CobCobradorService   {

	@Override
	public List<CobCobrador> buscarCobradorActivosLista(Long idEmpresa) {
		return repository.buscarCobradorActivosLista(idEmpresa);
	}

	

}
