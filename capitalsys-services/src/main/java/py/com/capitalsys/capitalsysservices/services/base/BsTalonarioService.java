package py.com.capitalsys.capitalsysservices.services.base;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface BsTalonarioService extends CommonService<BsTalonario> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<BsTalonario> buscarBsTalonarioActivosLista(Long idEmpresa);
	
	List<BsTalonario> buscarBsTalonarioPorModuloLista(Long idEmpresa, Long idModulo);
}
