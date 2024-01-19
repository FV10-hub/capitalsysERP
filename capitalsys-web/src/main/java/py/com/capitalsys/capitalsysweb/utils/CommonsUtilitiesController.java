package py.com.capitalsys.capitalsysweb.utils;

import java.util.List;
import java.util.Objects;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCaja;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.base.BsTalonarioService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobCajaService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobHabilitacionCajaService;
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

	@ManagedProperty("#{cobCajaServiceImpl}")
	private CobCajaService cobCajaServiceImpl;

	@ManagedProperty("#{cobHabilitacionCajaServiceImpl}")
	private CobHabilitacionCajaService cobHabilitacionCajaServiceImpl;

	private CobCaja cobCajaSelected;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	// METODOS
	public String getValorParametro(String nombreParametro, long moduloId) {
		String valorParametro = this.bsParametroServiceImpl.buscarParametro(nombreParametro,
				this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId(), moduloId);

		return valorParametro;
	}

	public Long getIdEmpresaLogueada() {
		return this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId();
	}

	public String getCodUsuarioLogueada() {
		return this.sessionBean.getUsuarioLogueado().getCodUsuario();
	}
	
	public CobCaja getCajaUsuarioLogueado() {
		this.cobCajaSelected = this.cobCajaServiceImpl.usuarioTieneCaja(this.sessionBean.getUsuarioLogueado().getId());
		return this.cobCajaSelected;
	}

	public boolean validarSiTengaHabilitacionAbierta() {
		this.cobCajaSelected = getCajaUsuarioLogueado();
		String valor = this.cobHabilitacionCajaServiceImpl
				.validaHabilitacionAbierta(this.sessionBean.getUsuarioLogueado().getId(), this.cobCajaSelected.getId());

		return valor.equalsIgnoreCase("N");

	}

	public CobHabilitacionCaja getHabilitacionAbierta() {
		this.cobCajaSelected = getCajaUsuarioLogueado();
		CobHabilitacionCaja valor = this.cobHabilitacionCajaServiceImpl.retornarHabilitacionAbierta(
				this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId(),
				this.sessionBean.getUsuarioLogueado().getId(), this.cobCajaSelected.getId());

		return valor;

	}

	// GETTERS Y SETTERS
	public BsParametroService getBsParametroServiceImpl() {
		return bsParametroServiceImpl;
	}

	public List<BsTalonario> bsTalonarioPorModuloLista(Long IdEmpresa, Long IdModulo) {
		return this.bsTalonarioServiceImpl.buscarBsTalonarioPorModuloLista(IdEmpresa, IdModulo);
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

	public CobCajaService getCobCajaServiceImpl() {
		return cobCajaServiceImpl;
	}

	public void setCobCajaServiceImpl(CobCajaService cobCajaServiceImpl) {
		this.cobCajaServiceImpl = cobCajaServiceImpl;
	}

	public CobHabilitacionCajaService getCobHabilitacionCajaServiceImpl() {
		return cobHabilitacionCajaServiceImpl;
	}

	public void setCobHabilitacionCajaServiceImpl(CobHabilitacionCajaService cobHabilitacionCajaServiceImpl) {
		this.cobHabilitacionCajaServiceImpl = cobHabilitacionCajaServiceImpl;
	}

}
