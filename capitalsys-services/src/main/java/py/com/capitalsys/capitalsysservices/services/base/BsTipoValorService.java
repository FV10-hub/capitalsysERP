package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface BsTipoValorService extends CommonService<BsTipoValor> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<BsTipoValor> buscarTipoValorActivosLista(Long idEmpresa);
}
