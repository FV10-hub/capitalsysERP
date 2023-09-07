package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;


public interface BsPersonaRepository extends PagingAndSortingRepository<BsPersona, Long> {

	BsPersona findByNombre(String nombre);
	
	@Query("SELECT m FROM BsPersona m")
	Page<BsPersona> buscarTodos(Pageable pageable);
	
	@Query("SELECT m FROM BsPersona m")
	List<BsPersona> buscarTodosLista();
	
	
}
