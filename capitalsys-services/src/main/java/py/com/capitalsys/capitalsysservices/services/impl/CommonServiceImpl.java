/**
 * 
 */
package py.com.capitalsys.capitalsysservices.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import py.com.capitalsys.capitalsysservices.services.CommonService;

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements CommonService<E> {

	@Autowired
	protected R repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<E> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional
	public List<E> saveAll(List<E> entities) {
		return (List<E>) repository.saveAll(entities);
	}

	@Override
	@Transactional
	public List<E> deleteAll(List<E> entities) {
		List<E> deletedEntities = new ArrayList<>();
	    
	    for (E entity : entities) {
	        repository.delete(entity);
	        deletedEntities.add(entity);
	    }

	    return deletedEntities;
	}

}
