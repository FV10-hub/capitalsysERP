package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobClienteRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCliente;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobClienteService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class CobClienteServiceImpl 
extends CommonServiceImpl<CobCliente, CobClienteRepository> implements CobClienteService   {

	@Override
	public List<CobCliente> buscarClienteActivosLista(Long idEmpresa) {
		return repository.buscarClienteActivosLista(idEmpresa);
	}

	

}
