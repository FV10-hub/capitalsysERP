package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrosValores;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 17 ene. 2024 - Elitebook
*/
public interface CobCobrosValoresService extends CommonService<CobCobrosValores> {

	List<CobCobrosValores> buscarTodosLista();

	List<CobCobrosValores> buscarCobCobrosValoresActivosLista(Long idEmpresa);

	List<CobCobrosValores> buscarValoresPorTipoSinDepositarLista(Long idEmpresa, String tipoComprobante);
	
	List<CobCobrosValores> buscarValoresPorComprobanteLista(Long idEmpresa,Long idComprobante, String tipoComprobante);

	List<CobCobrosValores> buscarValoresDepositoLista(Long idEmpresa,Long idDeposito);
	
}
