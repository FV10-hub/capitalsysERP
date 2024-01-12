package py.com.capitalsys.capitalsysservices.services.impl.tesoreria;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.tesoreria.TesBancoRepository;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesBanco;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.tesoreria.TesBancoService;

/*
* 12 ene. 2024 - Elitebook
*/
@Service
public class TesBancoServiceImpl extends CommonServiceImpl<TesBanco, TesBancoRepository> implements TesBancoService{

	@Override
	public List<TesBanco> buscarTesBancoActivosLista(Long idEmpresa) {
		return this.repository.buscarTesBancoActivosLista(idEmpresa);
	}

}
