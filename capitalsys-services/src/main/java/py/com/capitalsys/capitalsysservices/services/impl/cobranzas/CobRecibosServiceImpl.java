package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobRecibosRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobReciboCabecera;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobRecibosService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 15 ene. 2024 - Elitebook
*/
@Service
public class CobRecibosServiceImpl extends CommonServiceImpl<CobReciboCabecera, CobRecibosRepository> implements CobRecibosService{

	@Override
	public Long calcularNroReciboDisponible(Long idEmpresa, Long idTalonario) {
		return this.repository.calcularNroReciboDisponible(idEmpresa, idTalonario);
	}

	@Override
	public List<CobReciboCabecera> buscarCobReciboCabeceraActivosLista(Long idEmpresa) {
		return this.repository.buscarCobReciboCabeceraActivosLista(idEmpresa);
	}

	@Override
	public List<CobReciboCabecera> buscarCobReciboCabeceraPorClienteLista(Long idEmpresa, Long idCliente) {
		return this.repository.buscarCobReciboCabeceraPorClienteLista(idEmpresa, idCliente);
	}

	@Override
	public List<CobReciboCabecera> buscarCobReciboCabeceraPorCobradorLista(Long idEmpresa, Long idCobrador) {
		return this.repository.buscarCobReciboCabeceraPorCobradorLista(idEmpresa, idCobrador);
	}

	@Override
	public List<CobReciboCabecera> buscarCobReciboCabeceraPorNroReciboLista(Long idEmpresa, Long idTalonario,
			Long nroRecibo) {
		return this.repository.buscarCobReciboCabeceraPorNroReciboLista(idEmpresa, idTalonario, nroRecibo);
	}

}
