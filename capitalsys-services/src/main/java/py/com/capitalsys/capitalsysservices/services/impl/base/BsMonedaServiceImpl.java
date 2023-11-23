package py.com.capitalsys.capitalsysservices.services.impl.base;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.base.BsMonedaRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsMoneda;
import py.com.capitalsys.capitalsysservices.services.base.BsMonedaService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

@Service
public class BsMonedaServiceImpl 
extends CommonServiceImpl<BsMoneda, BsMonedaRepository> implements BsMonedaService   {

}
