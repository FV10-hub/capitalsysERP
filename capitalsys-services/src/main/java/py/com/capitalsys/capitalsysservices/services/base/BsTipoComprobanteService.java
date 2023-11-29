package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsTipoComprobante;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface BsTipoComprobanteService extends CommonService<BsTipoComprobante> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<BsTipoComprobante> buscarBsTipoComprobanteActivosLista(Long idEmpresa);
}
