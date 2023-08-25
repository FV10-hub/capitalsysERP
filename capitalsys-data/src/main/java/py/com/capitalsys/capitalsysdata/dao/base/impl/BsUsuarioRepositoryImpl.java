package py.com.capitalsys.capitalsysdata.dao.base.impl;

import py.com.capitalsys.capitalsysdata.dao.base.BsUsuarioRepository;
import py.com.capitalsys.capitalsysdata.dao.common.CommonRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

/*
* Aug 24, 2023 - 5:27:24 PM - fvazquez
* 
*/
public class BsUsuarioRepositoryImpl extends CommonRepository<BsUsuario, BsUsuarioRepository> {
	
	public BsUsuario findUsuarioAndPassword(String usuario, String password) {
		return this.repository.findByUsuarioAndPassword(usuario, password);
	}
	
}
