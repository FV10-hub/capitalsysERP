package py.com.capitalsys.capitalsysservices.services.stock;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.stock.StoAjusteInventarioCabecera;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface StoAjusteInventarioCabeceraService extends CommonService<StoAjusteInventarioCabecera> {

	List<StoAjusteInventarioCabecera> buscarTodosLista();

	Long calcularNroOperacionDisponible(Long idEmpresa);

	List<StoAjusteInventarioCabecera> buscarStoAjusteInventarioCabeceraActivosLista(Long idEmpresa);

}
