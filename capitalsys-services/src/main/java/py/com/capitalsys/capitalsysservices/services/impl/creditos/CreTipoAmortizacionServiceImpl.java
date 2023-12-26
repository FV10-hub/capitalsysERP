package py.com.capitalsys.capitalsysservices.services.impl.creditos;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.creditos.CreTipoAmortizacionRepository;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;
import py.com.capitalsys.capitalsysservices.services.creditos.CreTipoAmortizacionService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 26 dic. 2023 - Elitebook
*/
@Service
public class CreTipoAmortizacionServiceImpl extends CommonServiceImpl<CreTipoAmortizacion, CreTipoAmortizacionRepository> implements CreTipoAmortizacionService{

	@Override
	public List<CreTipoAmortizacion> buscarCreTipoAmortizacionActivosLista() {
		return this.repository.buscarCreTipoAmortizacionActivosLista();
	}

}
