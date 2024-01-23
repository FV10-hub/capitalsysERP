package py.com.capitalsys.capitalsysservices.services.tesoreria;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoCabecera;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface TesPagoService extends CommonService<TesPagoCabecera> {
	
	Long calcularNroPagoDisponible(Long idEmpresa, Long idTalonario);

	List<TesPagoCabecera> buscarTesPagoCabeceraActivosLista(Long idEmpresa);
	
	List<TesPagoCabecera> buscarTesPagoCabeceraPorBeneficiarioLista(Long idEmpresa, Long idBeneficiario);
	
	List<TesPagoCabecera> buscarTesPagoCabeceraPorTipoOperacionLista(Long idEmpresa, String tipoOperacion);
	
	//TesPagoCabecera recuperarPagosConDetalle(Long idEmpresa, Long idPago);

}
