package py.com.capitalsys.capitalsysweb.utils;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.base.BsTalonarioService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;

/*
* 5 ene. 2024 - Elitebook
*/
@ManagedBean
@SessionScoped
public class CommonsUtilitiesController {

	@ManagedProperty("#{bsParametroServiceImpl}")
	private BsParametroService bsParametroServiceImpl;
	
	@ManagedProperty("#{bsTalonarioServiceImpl}")
	private BsTalonarioService bsTalonarioServiceImpl;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	public String getValorParametro(String nombreParametro, long moduloId) {
		String valorParametro = this.bsParametroServiceImpl.buscarParametro(nombreParametro,
				this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId(), moduloId);

		return valorParametro;
	}
	
	public Long getIdEmpresaLogueada() {
		return this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId();
	}

	public BsParametroService getBsParametroServiceImpl() {
		return bsParametroServiceImpl;
	}
	
	public List<BsTalonario> bsTalonarioPorModuloLista(Long IdEmpresa, Long IdModulo){
		return this.bsTalonarioServiceImpl.buscarBsTalonarioPorModuloLista(IdEmpresa,IdModulo);
	}

	public void setBsParametroServiceImpl(BsParametroService bsParametroServiceImpl) {
		this.bsParametroServiceImpl = bsParametroServiceImpl;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public BsTalonarioService getBsTalonarioServiceImpl() {
		return bsTalonarioServiceImpl;
	}

	public void setBsTalonarioServiceImpl(BsTalonarioService bsTalonarioServiceImpl) {
		this.bsTalonarioServiceImpl = bsTalonarioServiceImpl;
	}
	

}
