package py.com.capitalsys.capitalsysservices.services.creditos;

import java.math.BigDecimal;
import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 4 ene. 2024 - Elitebook
*/
public interface CreDesembolsoService extends CommonService<CreDesembolsoCabecera>{

	BigDecimal calcularNroDesembolsoDisponible(Long idEmpresa);
	
	List<CreDesembolsoCabecera> buscarCreDesembolsoCabeceraActivosLista(Long idEmpresa);
	
	List<CreDesembolsoCabecera> buscarCreDesembolsoAFacturarLista(Long idEmpresa);
	
	List<CreDesembolsoCabecera> buscarCreDesembolsoParaPagosTesoreriarLista(Long idEmpresa, Long idCliente);
	
}
