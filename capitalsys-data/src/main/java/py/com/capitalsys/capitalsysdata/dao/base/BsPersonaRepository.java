package py.com.capitalsys.capitalsysdata.dao.base;

import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;


public interface BsPersonaRepository extends PagingAndSortingRepository<BsPersona, Long> {

	BsPersona findByNombre(String nombre);
	
	
}
