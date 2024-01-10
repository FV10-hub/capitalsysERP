package py.com.capitalsys.capitalsysdata.dao.ventas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaCabecera;

/*
* 9 ene. 2024 - Elitebook
*/
public interface VenFacturasRepository extends PagingAndSortingRepository<VenFacturaCabecera, Long> {
	@Query("SELECT m FROM VenFacturaCabecera m")
	Page<VenFacturaCabecera> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM VenFacturaCabecera m")
	List<VenFacturaCabecera> buscarTodosLista();

	@Query(value = "SELECT COALESCE(MAX(m.nro_factura), 0) + 1 FROM ven_facturas_cabecera m where bs_empresa_id = ?1 and bs_talonario_id = ?2", nativeQuery = true)
	Long calcularNroFacturaDisponible(Long idEmpresa, Long idTalonario);

	@Query("SELECT m FROM VenFacturaCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<VenFacturaCabecera> buscarVenFacturaCabeceraActivosLista(Long idEmpresa);
}
