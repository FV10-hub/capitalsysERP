package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrador;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface CobCobradorService extends CommonService<CobCobrador> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<CobCobrador> buscarCobradorActivosLista(Long idEmpresa);
}
