package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public interface BsUsuarioRepository extends PagingAndSortingRepository<BsUsuario, Long> {

	@Query("SELECT p FROM BsUsuario p WHERE p.codUsuario = ?1 AND p.password = ?2")
	BsUsuario findByUsuarioAndPassword(String codUsuario, String password);
	
	@Query("SELECT p FROM BsUsuario p WHERE p.codUsuario = ?1")
	BsUsuario findByUsuario(String codUsuario);
	
	@Query(value = "SELECT new py.com.capitalsys.capitalsysentities.dto.MenuDto(mi, per, u) " 
			+ "FROM BsUsuario u "
			+ "JOIN u.rol rol " 
			+ "JOIN rol.bsPermisoRol per " 
			+ "JOIN per.bsMenu men " 
			+ "JOIN men.bsMenuItem mi "
			+ "where u.id = ?1")
	List<MenuDto> findMenuByUser(Long idUsuario);
	
	
}
