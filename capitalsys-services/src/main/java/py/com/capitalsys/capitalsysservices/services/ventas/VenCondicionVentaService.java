package py.com.capitalsys.capitalsysservices.services.ventas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenCondicionVenta;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface VenCondicionVentaService extends CommonService<VenCondicionVenta> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<VenCondicionVenta> buscarVenCondicionVentaActivosLista(Long idEmpresa);
}
