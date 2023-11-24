/**
 * 
 */
package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;

/**
 * 
 */
public interface BsEmpresaRepository extends PagingAndSortingRepository<BsEmpresa, Long> {
	
	@Query("SELECT m FROM BsEmpresa m")
	Page<BsEmpresa> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsEmpresa m")
	List<BsEmpresa> buscarTodosLista();
	
	@Query("SELECT m FROM BsEmpresa m where m.estado = 'ACTIVO'")
	List<BsEmpresa> buscarEmpresaActivosLista();

}
