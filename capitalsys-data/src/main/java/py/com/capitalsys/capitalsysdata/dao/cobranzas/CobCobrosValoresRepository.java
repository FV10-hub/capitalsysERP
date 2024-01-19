package py.com.capitalsys.capitalsysdata.dao.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrosValores;

/*
* 17 ene. 2024 - Elitebook
*/
public interface CobCobrosValoresRepository extends PagingAndSortingRepository<CobCobrosValores, Long>{
	
	@Query("SELECT m FROM CobCobrosValores m")
	Page<CobCobrosValores> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CobCobrosValores m")
	List<CobCobrosValores> buscarTodosLista();
	
	@Query("SELECT m FROM CobCobrosValores m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<CobCobrosValores> buscarCobCobrosValoresActivosLista(Long idEmpresa);
	
	@Query("SELECT m FROM CobCobrosValores m where m.estado = 'ACTIVO' and m.indDepositado = 'N' and m.bsEmpresa.id = ?1 and m.tipoComprobante = ?2 ")
	List<CobCobrosValores> buscarValoresPorTipoSinDepositarLista(Long idEmpresa, String tipoComprobante);
	
	@Query("SELECT m FROM CobCobrosValores m where m.estado = 'ACTIVO' and m.indDepositado = 'N' and m.bsEmpresa.id = ?1 and m.idComprobate = ?2 and m.tipoComprobante = ?3 ")
	List<CobCobrosValores> buscarValoresPorComprobanteLista(Long idEmpresa,Long idComprobante, String tipoComprobante);
	
	@Query("SELECT m FROM CobCobrosValores m where m.estado = 'ACTIVO' and m.indDepositado = 'S' and m.bsEmpresa.id = ?1 and m.tesDeposito.id = ?2 ")
	List<CobCobrosValores> buscarValoresDepositoLista(Long idEmpresa,Long idDeposito);

}
