package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCaja;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface CobCajaService extends CommonService<CobCaja> {

	/*
	 * agregar aca los metodos personalizados
	 * public Curso findCursoByAlumnoId(Long id);
	 * */
	List<CobCaja> buscarTodosActivoLista();
	CobCaja usuarioTieneCaja(Long idUsuario);
}
