package py.com.capitalsys.capitalsysservices.services.tesoreria;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesBanco;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface TesBancoService extends CommonService<TesBanco> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<TesBanco> buscarTesBancoActivosLista(Long idEmpresa);
}
