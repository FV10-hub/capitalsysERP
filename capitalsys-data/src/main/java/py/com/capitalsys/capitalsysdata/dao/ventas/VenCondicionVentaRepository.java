package py.com.capitalsys.capitalsysdata.dao.ventas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenCondicionVenta;

public interface VenCondicionVentaRepository extends PagingAndSortingRepository<VenCondicionVenta, Long> {
	
	@Query("SELECT m FROM VenCondicionVenta m")
	Page<VenCondicionVenta> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM VenCondicionVenta m")
	List<VenCondicionVenta> buscarTodosLista();
	
	@Query("SELECT m FROM VenCondicionVenta m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<VenCondicionVenta> buscarVenCondicionVentaActivosLista(Long idEmpresa);
	
	
}
