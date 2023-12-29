package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface CobHabilitacionCajaService extends CommonService<CobHabilitacionCaja> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	BigDecimal calcularNroHabilitacionDisponible();
	
	String validaHabilitacionAbierta(Long idUsuario, Long idCaja);
	
	List<CobHabilitacionCaja> buscarCobHabilitacionCajaActivosLista(Long idEmpresa);
}
