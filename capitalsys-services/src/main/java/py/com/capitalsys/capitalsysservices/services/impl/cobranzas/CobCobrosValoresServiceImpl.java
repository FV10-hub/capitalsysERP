package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobCobrosValoresRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrosValores;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobCobrosValoresService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 12 ene. 2024 - Elitebook
*/
@Service
public class CobCobrosValoresServiceImpl extends CommonServiceImpl<CobCobrosValores, CobCobrosValoresRepository>
		implements CobCobrosValoresService {

	@Override
	public List<CobCobrosValores> buscarTodosLista() {
		return this.repository.buscarTodosLista();
	}

	@Override
	public List<CobCobrosValores> buscarCobCobrosValoresActivosLista(Long idEmpresa) {
		return this.repository.buscarCobCobrosValoresActivosLista(idEmpresa);
	}

	@Override
	public List<CobCobrosValores> buscarValoresPorTipoSinDepositarLista(Long idEmpresa, String tipoComprobante) {
		return this.repository.buscarValoresPorTipoSinDepositarLista(idEmpresa, tipoComprobante);
	}

	@Override
	public List<CobCobrosValores> buscarValoresPorComprobanteLista(Long idEmpresa, Long idComprobante,
			String tipoComprobante) {
		return this.repository.buscarValoresPorComprobanteLista(idEmpresa, idComprobante, tipoComprobante);
	}

}
