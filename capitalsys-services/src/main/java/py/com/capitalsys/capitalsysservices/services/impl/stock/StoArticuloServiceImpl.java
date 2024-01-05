package py.com.capitalsys.capitalsysservices.services.impl.stock;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.stock.StoArticuloRepository;
import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.stock.StoArticuloService;

@Service
public class StoArticuloServiceImpl 
extends CommonServiceImpl<StoArticulo, StoArticuloRepository> implements StoArticuloService   {

	@Override
	public List<StoArticulo> buscarStoArticuloActivosLista(Long idEmpresa) {
		return repository.buscarStoArticuloActivosLista(idEmpresa);
	}

	@Override
	public StoArticulo buscarArticuloPorCodigo(String param, Long empresaId) {
		return this.repository.buscarArticuloPorCodigo(param, empresaId);
	}

	

}
