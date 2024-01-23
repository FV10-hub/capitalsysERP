package py.com.capitalsys.capitalsysdata.dao.tesoreria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoCabecera;

public interface TesPagoRepository extends PagingAndSortingRepository<TesPagoCabecera, Long> {
	
	@Query("SELECT m FROM TesPagoCabecera m")
	Page<TesPagoCabecera> buscarTodos(Pageable pageable);

	@Query("SELECT m FROM TesPagoCabecera m")
	List<TesPagoCabecera> buscarTodosLista();
	
	@Query(value = "SELECT COALESCE(MAX(m.nro_pago), 0) + 1 FROM tes_pagos_cabecera m where m.bs_empresa_id = ?1 and m.bs_talonario_id = ?2", nativeQuery = true)
	Long calcularNroPagoDisponible(Long idEmpresa, Long idTalonario);

	@Query("SELECT m FROM TesPagoCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<TesPagoCabecera> buscarTesPagoCabeceraActivosLista(Long idEmpresa);
	
	@Query("SELECT m FROM TesPagoCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1 and m.idBeneficiario = ?2")
	List<TesPagoCabecera> buscarTesPagoCabeceraPorBeneficiarioLista(Long idEmpresa, Long idBeneficiario);
	
	@Query("SELECT m FROM TesPagoCabecera m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1 and m.tipoOperacion = ?2")
	List<TesPagoCabecera> buscarTesPagoCabeceraPorTipoOperacionLista(Long idEmpresa, String tipoOperacion);
	
	/*@Query("SELECT DISTINCT tc FROM TesPagoCabecera tc "
	        + "LEFT JOIN FETCH tc.tesPagoComprobanteDetallesList detalles "
	        + "LEFT JOIN FETCH tc.tesPagoValoresList valores "
	        + "WHERE tc.bsEmpresa.id = ?1 and tc.id = ?2 ")
	TesPagoCabecera recuperarPagosConDetalle(Long idEmpresa, Long idPago);*/


	
}
