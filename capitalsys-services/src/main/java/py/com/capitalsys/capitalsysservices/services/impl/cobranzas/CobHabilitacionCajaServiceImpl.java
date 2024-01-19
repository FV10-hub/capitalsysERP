package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobHabilitacionCajaRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobHabilitacionCajaService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 28 dic. 2023 - Elitebook
*/
@Service
public class CobHabilitacionCajaServiceImpl extends CommonServiceImpl<CobHabilitacionCaja, CobHabilitacionCajaRepository> implements CobHabilitacionCajaService {

	@Override
	public BigDecimal calcularNroHabilitacionDisponible() {
		return this.repository.calcularNroHabilitacionDisponible();
	}

	@Override
	public List<CobHabilitacionCaja> buscarCobHabilitacionCajaActivosLista(Long idEmpresa) {
		return this.repository.buscarCobHabilitacionCajaActivosLista(idEmpresa);
	}

	@Override
	public String validaHabilitacionAbierta(Long idUsuario, Long idCaja) {
		return this.repository.validaHabilitacionAbierta(idUsuario, idCaja);
	}

	@Override
	public CobHabilitacionCaja retornarHabilitacionAbierta(Long idEmpresa, Long idUsuario, Long idCaja) {
		return this.repository.retornarHabilitacionAbierta(idEmpresa, idUsuario, idCaja);
	}

	

}
