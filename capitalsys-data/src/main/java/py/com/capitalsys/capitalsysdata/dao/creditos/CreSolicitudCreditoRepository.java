package py.com.capitalsys.capitalsysdata.dao.creditos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreSolicitudCreditoRepository extends PagingAndSortingRepository<CreSolicitudCredito, Long> {
	
	@Query("SELECT m FROM CreSolicitudCredito m")
	Page<CreSolicitudCredito> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM CreSolicitudCredito m")
	List<CreSolicitudCredito> buscarTodosLista();
	
	@Query("SELECT m FROM CreSolicitudCredito m where m.estado = 'ACTIVO' and m.bsEmpresa.id = ?1")
	List<CreSolicitudCredito> buscarCobradorActivosLista(Long idEmpresa);

}
