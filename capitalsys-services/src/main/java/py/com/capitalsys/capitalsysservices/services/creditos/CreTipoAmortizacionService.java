package py.com.capitalsys.capitalsysservices.services.creditos;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreTipoAmortizacionService extends CommonService<CreTipoAmortizacion>{
	List<CreTipoAmortizacion> buscarCreTipoAmortizacionActivosLista();
}
