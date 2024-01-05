package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;

/**
 * fvazquez
 */
public interface BsTalonarioRepository extends PagingAndSortingRepository<BsTalonario, Long> {
	
	@Query("SELECT m FROM BsTalonario m")
	Page<BsTalonario> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsTalonario m")
	List<BsTalonario> buscarTodosLista();
	
	@Query("SELECT m FROM BsTalonario m where m.estado = 'ACTIVO' and m.bsTimbrado.bsEmpresa.id = ?1")
	List<BsTalonario> buscarBsTalonarioActivosLista(Long idEmpresa);
	
	@Query("SELECT m FROM BsTalonario m where m.estado = 'ACTIVO' and m.bsTimbrado.bsEmpresa.id = ?1 and m.bsTipoComprobante.bsModulo.id = ?2")
	List<BsTalonario> buscarBsTalonarioPorModuloLista(Long idEmpresa, Long idModulo);

}
