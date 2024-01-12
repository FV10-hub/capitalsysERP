package py.com.capitalsys.capitalsysservices.services.compras;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.compras.ComProveedor;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface ComProveedorService extends CommonService<ComProveedor> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<ComProveedor> buscarComProveedorActivosLista(Long idEmpresa);
}
