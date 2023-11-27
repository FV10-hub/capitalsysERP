package py.com.capitalsys.capitalsysservices.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsUsuarioRepository;
import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.LoginService;


/**
 * @author DevPredator
 * Clase que implementa las funciones para la logica de negocio para la pantalla de login.
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private BsUsuarioRepository bsUsuarioRepositoryImpl;
	
	@Override
	public BsUsuario consultarUsuarioLogin(String usuario, String password) {
		return this.bsUsuarioRepositoryImpl.findByUsuarioAndPassword(usuario, password);
	}

	@Override
	public List<MenuDto> consultarMenuPorUsuario(Long id) {
		return bsUsuarioRepositoryImpl.findMenuByUser(id);
	}

	@Override
	public BsUsuario findByUsuario(String codUsuario) {
		return this.bsUsuarioRepositoryImpl.findByUsuario(codUsuario);
	}

}
