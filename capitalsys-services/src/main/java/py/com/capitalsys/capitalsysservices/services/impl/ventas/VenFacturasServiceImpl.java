package py.com.capitalsys.capitalsysservices.services.impl.ventas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.ventas.VenFacturasRepository;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaCabecera;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.ventas.VenFacturasService;

/*
* 9 ene. 2024 - Elitebook
*/
@Service
public class VenFacturasServiceImpl extends CommonServiceImpl<VenFacturaCabecera, VenFacturasRepository>
		implements VenFacturasService {

	@Override
	public Long calcularNroFacturaDisponible(Long idEmpresa, Long idTalonario) {
		return this.repository.calcularNroFacturaDisponible(idEmpresa, idTalonario);
	}

	@Override
	public List<VenFacturaCabecera> buscarVenFacturaCabeceraActivosLista(Long idEmpresa) {
		return this.repository.buscarVenFacturaCabeceraActivosLista(idEmpresa);
	}

}
