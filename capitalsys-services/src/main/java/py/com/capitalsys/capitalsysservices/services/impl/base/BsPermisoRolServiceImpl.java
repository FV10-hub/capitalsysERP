package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsPermisoRolRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsPermisoRol;
import py.com.capitalsys.capitalsysservices.services.base.BsPermisoRolService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsPermisoRolServiceImpl extends CommonServiceImpl<BsPermisoRol, BsPermisoRolRepository> implements BsPermisoRolService  {

	@Override
	public Page<BsPermisoRol> listarTodos(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public List<BsPermisoRol> buscarTodosLista() {
		return repository.buscarTodosLista();
	}

}
