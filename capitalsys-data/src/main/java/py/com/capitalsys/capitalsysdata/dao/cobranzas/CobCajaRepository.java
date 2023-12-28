package py.com.capitalsys.capitalsysdata.dao.cobranzas;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCaja;

/*
* 28 dic. 2023 - Elitebook
*/
public interface CobCajaRepository extends PagingAndSortingRepository<CobCaja, Long> {
	@Query("SELECT m FROM CobCaja m where m.estado = 'ACTIVO'")
	List<CobCaja> buscarTodosActivoLista();
	
	@Query("SELECT m FROM CobCaja m where m.bsUsuario.id = ?1")
	CobCaja usuarioTieneCaja(Long idUsuario);
}
