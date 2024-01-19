package py.com.capitalsys.capitalsysservices.services.tesoreria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesDeposito;
import py.com.capitalsys.capitalsysservices.services.CommonService;

public interface TesDepositoService extends CommonService<TesDeposito> {

	Page<TesDeposito> buscarTodos(Pageable pageable);

	List<TesDeposito> buscarTodosLista();

	List<TesDeposito> buscarTesDepositoActivosLista(Long idEmpresa);

}
