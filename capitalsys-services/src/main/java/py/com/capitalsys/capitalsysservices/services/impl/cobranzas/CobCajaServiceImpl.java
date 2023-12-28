package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobCajaRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCaja;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobCajaService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 28 dic. 2023 - Elitebook
*/
@Service
public class CobCajaServiceImpl extends CommonServiceImpl<CobCaja,CobCajaRepository> implements CobCajaService{

	@Override
	public List<CobCaja> buscarTodosActivoLista() {
		return this.repository.buscarTodosActivoLista();
	}

	@Override
	public CobCaja usuarioTieneCaja(Long idUsuario) {
		return this.repository.usuarioTieneCaja(idUsuario);
	}

}
