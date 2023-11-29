package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsTipoComprobanteValorRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoComprobante;
import py.com.capitalsys.capitalsysservices.services.base.BsTipoComprobanteService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsTipoComprobanteServiceImpl 
extends CommonServiceImpl<BsTipoComprobante, BsTipoComprobanteValorRepository> implements BsTipoComprobanteService   {

	@Override
	public List<BsTipoComprobante> buscarBsTipoComprobanteActivosLista(Long idEmpresa) {
		return repository.buscarBsTipoComprobanteActivosLista(idEmpresa);
	}

}
