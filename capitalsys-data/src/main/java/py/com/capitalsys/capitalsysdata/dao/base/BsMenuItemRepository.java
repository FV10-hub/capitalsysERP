package py.com.capitalsys.capitalsysdata.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public interface BsMenuItemRepository extends PagingAndSortingRepository<BsMenuItem, Long> {
	
	@Query(value = "SELECT m FROM BsMenuItem m where m.tipoMenu IN ('DEFINICION','MOVIMIENTOS','REPORTES') AND m.bsModulo.id = ?1")
	List<BsMenuItem> findMenuAgrupado(Long idModulo);
	
	@Query(value = "SELECT m FROM BsMenuItem m where m.idMenuItem = ?1 ORDER BY m.bsMenu.nroOrden asc")
	List<BsMenuItem> findMenuItemAgrupado(Long idMenuItemAgrupador);
	
	
}
