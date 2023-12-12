package py.com.capitalsys.capitalsysservices.services.stock;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface StoArticuloService extends CommonService<StoArticulo> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<StoArticulo> buscarStoArticuloActivosLista(Long idEmpresa);
}
