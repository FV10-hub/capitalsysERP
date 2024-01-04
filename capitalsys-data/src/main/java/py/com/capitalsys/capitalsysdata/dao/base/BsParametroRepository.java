/**
 * 
 */
package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsParametro;

/**
 * 
 */
public interface BsParametroRepository extends PagingAndSortingRepository<BsParametro, Long> {

	@Query("SELECT m FROM BsParametro m")
	Page<BsParametro> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM BsParametro m")
	List<BsParametro> buscarTodosLista();

	@Query("SELECT m FROM BsParametro m where m.estado = 'ACTIVO' and m.parametro = ?1 and m.bsEmpresa.id = ?2 and m.bsModulo.id = ?3")
	BsParametro buscarParametro(Long paramId, Long empresaId, Long moduloId);

	@Query("SELECT m FROM BsParametro m where m.estado = 'ACTIVO'")
	List<BsParametro> buscarParametroActivosLista();

}
