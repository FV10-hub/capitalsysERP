package py.com.capitalsys.capitalsysdata.dao.tesoreria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesBanco;


public interface TesBancoRepository extends PagingAndSortingRepository<TesBanco, Long> {
	
	@Query("SELECT m FROM TesBanco m")
	Page<TesBanco> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM TesBanco m")
	List<TesBanco> buscarTodosLista();
	
	@Query("SELECT m FROM TesBanco m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<TesBanco> buscarTesBancoActivosLista(Long idEmpresa);
	
	
}
