package py.com.capitalsys.capitalsysdata.dao.tesoreria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesDeposito;

public interface TesDepositoRepository extends PagingAndSortingRepository<TesDeposito, Long> {
	
	@Query("SELECT m FROM TesDeposito m")
	Page<TesDeposito> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM TesDeposito m")
	List<TesDeposito> buscarTodosLista();

	@Query("SELECT m FROM TesDeposito m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<TesDeposito> buscarTesDepositoActivosLista(Long idEmpresa);
	
}
