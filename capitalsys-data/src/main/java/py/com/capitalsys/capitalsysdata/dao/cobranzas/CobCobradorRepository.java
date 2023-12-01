package py.com.capitalsys.capitalsysdata.dao.cobranzas;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrador;

public interface CobCobradorRepository extends PagingAndSortingRepository<CobCobrador, Long> {
	
	@Query("SELECT m FROM CobCobrador m")
	Page<CobCobrador> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CobCobrador m")
	List<CobCobrador> buscarTodosLista();
	
	@Query("SELECT m FROM CobCobrador m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<CobCobrador> buscarCobradorActivosLista(Long idEmpresa);
	
	
}
