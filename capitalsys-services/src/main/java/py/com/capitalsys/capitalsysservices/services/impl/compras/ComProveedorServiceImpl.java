package py.com.capitalsys.capitalsysservices.services.impl.compras;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.compras.ComProveedorRepository;
import py.com.capitalsys.capitalsysentities.entities.compras.ComProveedor;
import py.com.capitalsys.capitalsysservices.services.compras.ComProveedorService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 12 ene. 2024 - Elitebook
*/
@Service
public class ComProveedorServiceImpl extends CommonServiceImpl<ComProveedor, ComProveedorRepository> implements ComProveedorService {

	@Override
	public List<ComProveedor> buscarComProveedorActivosLista(Long idEmpresa) {
		return this.repository.buscarComProveedorActivosLista(idEmpresa);
	}

}
