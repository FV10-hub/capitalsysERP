package py.com.capitalsys.capitalsysservices.services.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsMenuItemRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.base.BsMenuItemService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsMenuItemServiceImpl extends CommonServiceImpl<BsMenuItem, BsMenuItemRepository> implements BsMenuItemService   {
	
	@Override
	public List<BsMenuItem> findMenuAgrupado(Long idModulo) {
		return repository.findMenuAgrupado(idModulo);
	}

	@Override
	public List<BsMenuItem> findMenuItemAgrupado(Long idMenuItemAgrupador) {
		return repository.findMenuItemAgrupado(idMenuItemAgrupador);
	}
}
