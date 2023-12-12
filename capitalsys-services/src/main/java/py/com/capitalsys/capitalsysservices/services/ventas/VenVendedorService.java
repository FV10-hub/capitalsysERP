package py.com.capitalsys.capitalsysservices.services.ventas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface VenVendedorService extends CommonService<VenVendedor> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<VenVendedor> buscarVenVendedorActivosLista(Long idEmpresa);
}
