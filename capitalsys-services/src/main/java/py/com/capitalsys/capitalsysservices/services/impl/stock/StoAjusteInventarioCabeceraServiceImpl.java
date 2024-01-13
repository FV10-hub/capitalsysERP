package py.com.capitalsys.capitalsysservices.services.impl.stock;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.stock.StoAjusteInventarioCabeceraRepository;
import py.com.capitalsys.capitalsysentities.entities.stock.StoAjusteInventarioCabecera;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.stock.StoAjusteInventarioCabeceraService;

/*
* 9 ene. 2024 - Elitebook
*/
@Service
public class StoAjusteInventarioCabeceraServiceImpl extends CommonServiceImpl<StoAjusteInventarioCabecera, StoAjusteInventarioCabeceraRepository>
		implements StoAjusteInventarioCabeceraService {

	@Override
	public List<StoAjusteInventarioCabecera> buscarStoAjusteInventarioCabeceraActivosLista(Long idEmpresa) {
		return this.repository.buscarStoAjusteInventarioCabeceraActivosLista(idEmpresa);
	}

	@Override
	public List<StoAjusteInventarioCabecera> buscarTodosLista() {
		return this.repository.buscarTodosLista();
	}

	@Override
	public Long calcularNroOperacionDisponible(Long idEmpresa) {
		return this.repository.calcularNroOperacionDisponible(idEmpresa);
	}


}
