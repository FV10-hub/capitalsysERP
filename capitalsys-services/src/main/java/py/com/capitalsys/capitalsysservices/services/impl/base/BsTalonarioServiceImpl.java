package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsTalonarioRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysservices.services.base.BsTalonarioService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 2 ene. 2024 - Elitebook
*/
@Service
public class BsTalonarioServiceImpl 
extends CommonServiceImpl<BsTalonario, BsTalonarioRepository> implements BsTalonarioService {

	@Override
	public List<BsTalonario> buscarBsTalonarioActivosLista(Long idEmpresa) {
		return this.repository.buscarBsTalonarioActivosLista(idEmpresa);
	}

}
