package py.com.capitalsys.capitalsysservices.services.impl.ventas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.ventas.VenVendedorRepository;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.ventas.VenVendedorService;

@Service
public class VenVendedorServiceImpl 
extends CommonServiceImpl<VenVendedor, VenVendedorRepository> implements VenVendedorService   {

	@Override
	public List<VenVendedor> buscarVenVendedorActivosLista(Long idEmpresa) {
		return repository.buscarVenVendedorActivosLista(idEmpresa);
	}

	

}
