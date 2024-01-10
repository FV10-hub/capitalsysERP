package py.com.capitalsys.capitalsysservices.services.ventas;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaCabecera;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 4 ene. 2024 - Elitebook
*/
public interface VenFacturasService extends CommonService<VenFacturaCabecera>{

	Long calcularNroFacturaDisponible(Long idEmpresa, Long idTalonario);

	List<VenFacturaCabecera> buscarVenFacturaCabeceraActivosLista(Long idEmpresa);
	
}
