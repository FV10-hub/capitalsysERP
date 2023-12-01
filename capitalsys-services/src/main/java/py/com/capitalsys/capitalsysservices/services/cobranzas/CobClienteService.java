package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCliente;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface CobClienteService extends CommonService<CobCliente> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<CobCliente> buscarClienteActivosLista(Long idEmpresa);
}
