package py.com.capitalsys.capitalsysdata.dao.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobSaldo;

/*
* 12 ene. 2024 - Elitebook
*/
public interface CobSaldoRepository extends PagingAndSortingRepository<CobSaldo, Long>{
	
	@Query("SELECT m FROM CobSaldo m")
	Page<CobSaldo> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CobSaldo m")
	List<CobSaldo> buscarTodosLista();
	
	@Query(value = "SELECT SUM(COALESCE(m.saldo_cuota, 0)) FROM cob_saldos m where bs_empresa_id = ?1 and cob_cliente_id = ?2 ", nativeQuery = true)
	BigDecimal calcularTotalSaldoAFecha(Long idEmpresa, Long idCliente);
	
	@Query("SELECT m FROM CobSaldo m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<CobSaldo> buscarCobSaldoActivosLista(Long idEmpresa);
	
	@Query("SELECT m FROM CobSaldo m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1 and m.cobCliente.id = ?2 ")
	List<CobSaldo> buscarSaldoPorClienteLista(Long idEmpresa, Long idCliente);
	
	@Query("SELECT m FROM CobSaldo m where m.estado = 'ACTIVO' and m.saldoCuota > 0 and m.bsEmpresa.id = ?1 and m.cobCliente.id = ?2 ")
	List<CobSaldo> buscarSaldoPorClienteMayorACeroLista(Long idEmpresa, Long idCliente);

}
