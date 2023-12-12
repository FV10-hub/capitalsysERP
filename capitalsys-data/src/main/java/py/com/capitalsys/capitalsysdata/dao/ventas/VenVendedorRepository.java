package py.com.capitalsys.capitalsysdata.dao.ventas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;

public interface VenVendedorRepository extends PagingAndSortingRepository<VenVendedor, Long> {
	
	@Query("SELECT m FROM VenVendedor m")
	Page<VenVendedor> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM VenVendedor m")
	List<VenVendedor> buscarTodosLista();
	
	@Query("SELECT m FROM VenVendedor m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<VenVendedor> buscarVenVendedorActivosLista(Long idEmpresa);
	
	
}
