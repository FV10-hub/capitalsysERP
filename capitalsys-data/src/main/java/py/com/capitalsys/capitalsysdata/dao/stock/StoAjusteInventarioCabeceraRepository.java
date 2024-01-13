package py.com.capitalsys.capitalsysdata.dao.stock;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.stock.StoAjusteInventarioCabecera;

public interface StoAjusteInventarioCabeceraRepository extends PagingAndSortingRepository<StoAjusteInventarioCabecera, Long> {
	
	@Query("SELECT m FROM StoAjusteInventarioCabecera m")
	Page<StoAjusteInventarioCabecera> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM StoAjusteInventarioCabecera m")
	List<StoAjusteInventarioCabecera> buscarTodosLista();

	@Query(value = "SELECT COALESCE(MAX(m.nro_operacion), 0) + 1 FROM sto_ajuste_inventarios_cabecera m where bs_empresa_id = ?1", nativeQuery = true)
	Long calcularNroOperacionDisponible(Long idEmpresa);

	@Query("SELECT m FROM StoAjusteInventarioCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<StoAjusteInventarioCabecera> buscarStoAjusteInventarioCabeceraActivosLista(Long idEmpresa);
	
}
