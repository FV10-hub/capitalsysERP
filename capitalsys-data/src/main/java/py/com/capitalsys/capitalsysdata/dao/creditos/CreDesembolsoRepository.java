package py.com.capitalsys.capitalsysdata.dao.creditos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;

/*
* 4 ene. 2024 - Elitebook
*/
public interface CreDesembolsoRepository extends PagingAndSortingRepository<CreDesembolsoCabecera, Long> {

	@Query("SELECT m FROM CreDesembolsoCabecera m")
	Page<CreDesembolsoCabecera> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CreDesembolsoCabecera m")
	List<CreDesembolsoCabecera> buscarTodosLista();
	
	@Query(value = "SELECT COALESCE(MAX(m.nro_desembolso), 0) + 1 FROM cre_desembolso_cabecera m where bs_empresa_id = ?1", nativeQuery = true)
	BigDecimal calcularNroDesembolsoDisponible(Long idEmpresa);
	
	@Query("SELECT m FROM CreDesembolsoCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<CreDesembolsoCabecera> buscarCreDesembolsoCabeceraActivosLista(Long idEmpresa);
	
	@Query("SELECT m FROM CreDesembolsoCabecera m where m.estado = 'ACTIVO' and m.indDesembolsado = 'N' and m.bsEmpresa.id = ?1")
	List<CreDesembolsoCabecera> buscarCreDesembolsoAFacturarLista(Long idEmpresa);
	
	@Query("SELECT m FROM CreDesembolsoCabecera m where m.estado = 'ACTIVO' and m.indDesembolsado = 'S' and m.indFacturado = 'S' and m.bsEmpresa.id = ?1 and m.creSolicitudCredito.cobCliente.id = ?2")
	List<CreDesembolsoCabecera> buscarCreDesembolsoParaPagosTesoreriarLista(Long idEmpresa, Long idCliente);
	
}
