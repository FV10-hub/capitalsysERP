package py.com.capitalsys.capitalsysdata.dao.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;

/*
* 28 dic. 2023 - Elitebook
*/
public interface CobHabilitacionCajaRepository extends PagingAndSortingRepository<CobHabilitacionCaja, Long> {
	@Query("SELECT m FROM CobHabilitacionCaja m")
	Page<CobHabilitacionCaja> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM CobHabilitacionCaja m")
	List<CobHabilitacionCaja> buscarTodosLista();

	@Query(value = "SELECT COALESCE(MAX(m.nro_habilitacion), 0) + 1 FROM cob_habilitaciones_cajas m", nativeQuery = true)
	BigDecimal calcularNroHabilitacionDisponible();

	@Query(value = "SELECT COALESCE(MAX('S'), 'N') FROM cob_habilitaciones_cajas m where m.bs_usuario_id = ?1 and m.bs_cajas_id = ?2 and m.ind_cerrado = 'N'", nativeQuery = true)
	String validaHabilitacionAbierta(Long idUsuario, Long idCaja);

	@Query("SELECT m FROM CobHabilitacionCaja m where m.estado = 'ACTIVO' and m.bsUsuario.bsEmpresa.id = ?1")
	List<CobHabilitacionCaja> buscarCobHabilitacionCajaActivosLista(Long idEmpresa);
}
