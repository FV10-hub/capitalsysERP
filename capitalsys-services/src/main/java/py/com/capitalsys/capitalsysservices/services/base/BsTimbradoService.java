package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface BsTimbradoService extends CommonService<BsTimbrado> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<BsTimbrado> buscarBsTimbradoActivosLista(Long idEmpresa);
}
