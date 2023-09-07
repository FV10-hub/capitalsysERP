package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public interface BsUsuarioRepository extends PagingAndSortingRepository<BsUsuario, Long> {

	@Query("SELECT p FROM BsUsuario p WHERE p.codUsuario = ?1 AND p.password = ?2")
	BsUsuario findByUsuarioAndPassword(String codUsuario, String password);
	
	@Query(value = "SELECT new py.com.capitalsys.capitalsysentities.dto.MenuDto(u, rol, per, me) "
			+ "FROM bs_usuario u "
			+ "inner join bs_rol rol "
			+ "on u.id_bs_rol = rol.id "
			+ "inner join bs_permiso_rol per "
			+ "ON per.id_bs_rol = rol.id "
			+ "inner join bs_menu me "
			+ "on per.id_bs_menu = me.id "
			+ "where u.id = ?1", nativeQuery = true)
	List<MenuDto> findMenuByUser(Long idUsuario);
}
