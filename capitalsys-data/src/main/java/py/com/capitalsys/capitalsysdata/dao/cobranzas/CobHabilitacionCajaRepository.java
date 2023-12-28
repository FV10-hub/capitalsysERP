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

	@Query(value = "SELECT sum(COALESCE(m.nro_habilitacion, 0)) FROM cob_habilitaciones_cajas m", nativeQuery = true)
	BigDecimal calcularNroHabilitacionDisponible();

	@Query("SELECT m FROM CobHabilitacionCaja m where m.estado = 'ACTIVO' and m.bsUsuario.bsEmpresa.id = ?1")
	List<CobHabilitacionCaja> buscarCobHabilitacionCajaActivosLista(Long idEmpresa);
}
