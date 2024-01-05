package py.com.capitalsys.capitalsysservices.services.impl.creditos;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.creditos.CreSolicitudCreditoRepository;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;
import py.com.capitalsys.capitalsysservices.services.creditos.CreSolicitudCreditoService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 26 dic. 2023 - Elitebook
*/
@Service
public class CreSolicitudCreditoServiceImpl 
extends CommonServiceImpl<CreSolicitudCredito, CreSolicitudCreditoRepository> implements CreSolicitudCreditoService {

	@Override
	public List<CreSolicitudCredito> buscarSolicitudActivosLista(Long idEmpresa) {
		return this.repository.buscarSolicitudActivosLista(idEmpresa);
	}

	@Override
	public List<CreSolicitudCredito> buscarSolicitudAutorizadosLista(Long idEmpresa) {
		return this.repository.buscarSolicitudAutorizadosLista(idEmpresa);
	}

}
