package py.com.capitalsys.capitalsysservices.services.impl.creditos;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.creditos.CreMotivoPrestamoRepository;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreMotivoPrestamo;
import py.com.capitalsys.capitalsysservices.services.creditos.CreMotivoPrestamoService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 26 dic. 2023 - Elitebook
*/
@Service
public class CreMotivoPrestamoServiceImpl extends CommonServiceImpl<CreMotivoPrestamo, CreMotivoPrestamoRepository> implements CreMotivoPrestamoService{

	@Override
	public List<CreMotivoPrestamo> buscarCreMotivoPrestamoActivosLista() {
		return this.repository.buscarCreMotivoPrestamoActivosLista();
	}

}
