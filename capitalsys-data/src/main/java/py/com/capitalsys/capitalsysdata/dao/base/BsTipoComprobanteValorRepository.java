package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsTipoComprobante;

/**
 * fvazquez
 */
public interface BsTipoComprobanteValorRepository extends PagingAndSortingRepository<BsTipoComprobante, Long> {
	
	@Query("SELECT m FROM BsTipoComprobante m")
	Page<BsTipoComprobante> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsTipoComprobante m")
	List<BsTipoComprobante> buscarTodosLista();
	
	@Query("SELECT m FROM BsTipoComprobante m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<BsTipoComprobante> buscarBsTipoComprobanteActivosLista(Long idEmpresa);

}
