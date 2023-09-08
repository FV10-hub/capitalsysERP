package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public interface BsMenuItemRepository extends PagingAndSortingRepository<BsUsuario, Long> {

	@Query("SELECT p FROM BsUsuario p WHERE p.codUsuario = ?1 AND p.password = ?2")
	BsUsuario findByUsuarioAndPassword(String codUsuario, String password);

	@Query(value = "SELECT new py.com.capitalsys.capitalsysentities.dto.MenuDto(mi, per, u) " 
			+ "FROM BsUsuario u "
			+ "JOIN u.rol rol " 
			+ "JOIN rol.bsPermisoRol per " 
			+ "JOIN per.bsMenu men " 
			+ "JOIN men.bsMenuItem mi "
			+ "where u.id = ?1")
	List<MenuDto> findMenuByUser(Long idUsuario);
	
	@Query(value = "SELECT m FROM BsMenuItem m where m.tipoMenu IN ('DEFINICION','MOVIMIENTOS','REPORTES') AND m.bsModulo.id = ?1")
	List<BsMenuItem> findMenuAgrupado(Long idModulo);
	
	@Query(value = "SELECT m FROM BsMenuItem m where m.idMenuItem = ?1")
	List<BsMenuItem> findMenuItemAgrupado(Long idMenuItemAgrupador);
	
	
}
