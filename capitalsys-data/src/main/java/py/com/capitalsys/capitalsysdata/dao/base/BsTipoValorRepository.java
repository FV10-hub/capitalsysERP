package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;

/**
 * fvazquez
 */
public interface BsTipoValorRepository extends PagingAndSortingRepository<BsTipoValor, Long> {
	
	@Query("SELECT m FROM BsTipoValor m")
	Page<BsTipoValor> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsTipoValor m")
	List<BsTipoValor> buscarTodosLista();
	
	@Query("SELECT m FROM BsTipoValor m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<BsTipoValor> buscarTipoValorActivosLista(Long idEmpresa);

}
