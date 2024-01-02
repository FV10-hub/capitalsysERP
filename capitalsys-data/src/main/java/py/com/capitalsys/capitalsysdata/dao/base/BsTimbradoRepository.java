package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;

/**
 * fvazquez
 */
public interface BsTimbradoRepository extends PagingAndSortingRepository<BsTimbrado, Long> {
	
	@Query("SELECT m FROM BsTimbrado m")
	Page<BsTimbrado> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsTimbrado m")
	List<BsTimbrado> buscarTodosLista();
	
	@Query("SELECT m FROM BsTimbrado m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<BsTimbrado> buscarBsTimbradoActivosLista(Long idEmpresa);

}
