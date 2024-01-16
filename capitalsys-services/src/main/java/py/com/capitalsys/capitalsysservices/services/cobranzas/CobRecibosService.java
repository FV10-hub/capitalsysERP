package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobReciboCabecera;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 15 ene. 2024 - Elitebook
*/
public interface CobRecibosService extends CommonService<CobReciboCabecera>{
	
	Long calcularNroReciboDisponible(Long idEmpresa, Long idTalonario);

	List<CobReciboCabecera> buscarCobReciboCabeceraActivosLista(Long idEmpresa);
	
	List<CobReciboCabecera> buscarCobReciboCabeceraPorClienteLista(Long idEmpresa, Long idCliente);
	
	List<CobReciboCabecera> buscarCobReciboCabeceraPorCobradorLista(Long idEmpresa, Long idCobrador);
	
	List<CobReciboCabecera> buscarCobReciboCabeceraPorNroReciboLista(Long idEmpresa, Long idTalonario, Long nroRecibo);

}
