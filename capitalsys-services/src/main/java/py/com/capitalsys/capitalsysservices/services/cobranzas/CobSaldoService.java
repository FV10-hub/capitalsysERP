package py.com.capitalsys.capitalsysservices.services.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobSaldo;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 12 ene. 2024 - Elitebook
*/
public interface CobSaldoService extends CommonService<CobSaldo>{
	
	BigDecimal calcularTotalSaldoAFecha(Long idEmpresa, Long idCliente);
	
	List<CobSaldo> buscarCobSaldoActivosLista(Long idEmpresa);
	
	List<CobSaldo> buscarSaldoPorClienteLista(Long idEmpresa, Long idCliente);
	
	List<CobSaldo> buscarSaldoPorClienteMayorACeroLista(Long idEmpresa, Long idCliente);

}
