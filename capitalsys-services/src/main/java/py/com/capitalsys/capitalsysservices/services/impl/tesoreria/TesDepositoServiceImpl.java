package py.com.capitalsys.capitalsysservices.services.impl.tesoreria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.tesoreria.TesDepositoRepository;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesDeposito;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;
import py.com.capitalsys.capitalsysservices.services.tesoreria.TesDepositoService;

/*
* 18 ene. 2024 - Elitebook
*/
@Service
public class TesDepositoServiceImpl extends CommonServiceImpl<TesDeposito, TesDepositoRepository> implements TesDepositoService{

	@Override
	public List<TesDeposito> buscarTesDepositoActivosLista(Long idEmpresa) {
		return this.repository.buscarTesDepositoActivosLista(idEmpresa);
	}

	@Override
	public Page<TesDeposito> buscarTodos(Pageable pageable) {
		return this.repository.buscarTodos(pageable);
	}

	@Override
	public List<TesDeposito> buscarTodosLista() {
		return this.repository.buscarTodosLista();
	}

	

}
