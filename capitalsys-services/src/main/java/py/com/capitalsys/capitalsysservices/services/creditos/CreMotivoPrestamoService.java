package py.com.capitalsys.capitalsysservices.services.creditos;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreMotivoPrestamo;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreMotivoPrestamoService extends CommonService<CreMotivoPrestamo> {
	List<CreMotivoPrestamo> buscarCreMotivoPrestamoActivosLista();
}
