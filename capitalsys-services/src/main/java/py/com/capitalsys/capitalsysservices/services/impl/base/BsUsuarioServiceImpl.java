package py.com.capitalsys.capitalsysservices.services.impl.base;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsUsuarioRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.base.BsUsuarioService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsUsuarioServiceImpl extends CommonServiceImpl<BsUsuario, BsUsuarioRepository> implements BsUsuarioService   {

}
