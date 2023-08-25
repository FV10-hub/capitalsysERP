package py.com.capitalsys.capitalsysdata.dao.base.impl;

import py.com.capitalsys.capitalsysdata.dao.base.BsPersonaRepository;
import py.com.capitalsys.capitalsysdata.dao.common.CommonRepository;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;

/*
* Aug 24, 2023 - 5:27:24 PM - fvazquez
* 
*/
public class BsPersonaRepositoryImpl extends CommonRepository<BsPersona, BsPersonaRepository> {
	
	public BsPersona findByNombre(String nombre) {
		return this.repository.findByNombre(nombre);
	}
	
}
