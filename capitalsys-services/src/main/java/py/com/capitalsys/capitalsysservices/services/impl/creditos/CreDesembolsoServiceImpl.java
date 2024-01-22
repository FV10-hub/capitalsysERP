package py.com.capitalsys.capitalsysservices.services.impl.creditos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.creditos.CreDesembolsoRepository;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 4 ene. 2024 - Elitebook
*/
@Service
public class CreDesembolsoServiceImpl 
extends CommonServiceImpl<CreDesembolsoCabecera, CreDesembolsoRepository> implements CreDesembolsoService{

	@Override
	public BigDecimal calcularNroDesembolsoDisponible(Long idEmpresa) {
		return this.repository.calcularNroDesembolsoDisponible(idEmpresa);
	}

	@Override
	public List<CreDesembolsoCabecera> buscarCreDesembolsoCabeceraActivosLista(Long idEmpresa) {
		return this.repository.buscarCreDesembolsoCabeceraActivosLista(idEmpresa);
	}

	@Override
	public List<CreDesembolsoCabecera> buscarCreDesembolsoAFacturarLista(Long idEmpresa) {
		return this.repository.buscarCreDesembolsoAFacturarLista(idEmpresa);
	}

	@Override
	public List<CreDesembolsoCabecera> buscarCreDesembolsoParaPagosTesoreriarLista(Long idEmpresa, Long idCliente) {
		return this.repository.buscarCreDesembolsoParaPagosTesoreriarLista(idEmpresa, idCliente);
	}

}
