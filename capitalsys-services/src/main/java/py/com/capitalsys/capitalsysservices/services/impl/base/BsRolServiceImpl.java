package py.com.capitalsys.capitalsysservices.services.impl.base;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsRolRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsRol;
import py.com.capitalsys.capitalsysservices.services.base.BsRolService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsRolServiceImpl extends CommonServiceImpl<BsRol, BsRolRepository> implements BsRolService  {

}
