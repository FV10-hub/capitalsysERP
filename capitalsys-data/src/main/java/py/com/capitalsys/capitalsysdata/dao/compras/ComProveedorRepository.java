package py.com.capitalsys.capitalsysdata.dao.compras;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.compras.ComProveedor;


public interface ComProveedorRepository extends PagingAndSortingRepository<ComProveedor, Long> {
	
	@Query("SELECT m FROM ComProveedor m")
	Page<ComProveedor> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM ComProveedor m")
	List<ComProveedor> buscarTodosLista();
	
	@Query("SELECT m FROM ComProveedor m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<ComProveedor> buscarComProveedorActivosLista(Long idEmpresa);
	
	
}
