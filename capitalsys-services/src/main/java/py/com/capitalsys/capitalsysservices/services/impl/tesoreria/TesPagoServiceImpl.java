package py.com.capitalsys.capitalsysservices.services.impl.tesoreria;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.tesoreria.TesPagoRepository;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoCabecera;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.tesoreria.TesPagoService;

/*
* 18 ene. 2024 - Elitebook
*/
@Service
public class TesPagoServiceImpl extends CommonServiceImpl<TesPagoCabecera, TesPagoRepository> implements TesPagoService{

	@Override
	public List<TesPagoCabecera> buscarTesPagoCabeceraActivosLista(Long idEmpresa) {
		return this.repository.buscarTesPagoCabeceraActivosLista(idEmpresa);
	}

	@Override
	public List<TesPagoCabecera> buscarTesPagoCabeceraPorBeneficiarioLista(Long idEmpresa, Long idBeneficiario) {
		return this.repository.buscarTesPagoCabeceraPorBeneficiarioLista(idEmpresa, idBeneficiario);
	}

	@Override
	public List<TesPagoCabecera> buscarTesPagoCabeceraPorTipoOperacionLista(Long idEmpresa, String tipoOperacion) {
		return this.repository.buscarTesPagoCabeceraPorTipoOperacionLista(idEmpresa, tipoOperacion);
	}

	@Override
	public Long calcularNroPagoDisponible(Long idEmpresa, Long idTalonario) {
		return this.repository.calcularNroPagoDisponible(idEmpresa, idTalonario);
	}

	/*@Override
	public TesPagoCabecera recuperarPagosConDetalle(Long idEmpresa, Long idPago) {
		return this.repository.recuperarPagosConDetalle(idEmpresa, idPago);
	}*/

}
