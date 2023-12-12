package py.com.capitalsys.capitalsysservices.services.impl.ventas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.ventas.VenCondicionVentaRepository;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenCondicionVenta;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.ventas.VenCondicionVentaService;

@Service
public class VenCondicionVentaServiceImpl 
extends CommonServiceImpl<VenCondicionVenta, VenCondicionVentaRepository> implements VenCondicionVentaService   {

	@Override
	public List<VenCondicionVenta> buscarVenCondicionVentaActivosLista(Long idEmpresa) {
		return repository.buscarVenCondicionVentaActivosLista(idEmpresa);
	}

	

}
