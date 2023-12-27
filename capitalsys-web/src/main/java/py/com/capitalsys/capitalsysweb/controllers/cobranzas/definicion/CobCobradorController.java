package py.com.capitalsys.capitalsysweb.controllers.cobranzas.definicion;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrador;
import py.com.capitalsys.capitalsysservices.services.base.BsPersonaService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobCobradorService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 30 nov. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class CobCobradorController {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(CobCobradorController.class);

	private CobCobrador cobCobrador, cobCobradorSelected;
	private LazyDataModel<CobCobrador> lazyModel;
	private LazyDataModel<BsPersona> lazyPersonaList;
	
	private BsPersona bsPersonaSelected;
	private boolean esNuegoRegistro;
	
	private List<String> estadoList;

	private static final String DT_NAME = "dt-cobrador";
	private static final String DT_DIALOG_NAME = "manageCobradorDialog";
		
	@ManagedProperty("#{bsPersonaServiceImpl}")
	private BsPersonaService bsPersonaServiceImpl;

	@ManagedProperty("#{cobCobradorServiceImpl}")
	private CobCobradorService cobCobradorServiceImpl;
	
	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		this.cleanFields();

	}
	
	public void cleanFields() {
		this.cobCobrador = null;
		this.cobCobradorSelected = null;
		this.bsPersonaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;
		this.lazyPersonaList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	//GETTERS Y SETTERS
	public CobCobrador getCobCobrador() {
		if (Objects.isNull(cobCobrador)) {
			this.cobCobrador = new CobCobrador();
			this.cobCobrador.setBsEmpresa(new BsEmpresa());
			this.cobCobrador.setBsPersona(new BsPersona());
		}
		return cobCobrador;
	}

	public void setCobCobrador(CobCobrador cobCobrador) {
		this.cobCobrador = cobCobrador;
	}

	public CobCobrador getCobCobradorSelected() {
		if (Objects.isNull(cobCobradorSelected)) {
			this.cobCobradorSelected = new CobCobrador();
			this.cobCobradorSelected.setBsEmpresa(new BsEmpresa());
			this.cobCobradorSelected.setBsPersona(new BsPersona());
		}
		return cobCobradorSelected;
	}

	public void setCobCobradorSelected(CobCobrador cobCobradorSelected) {
		if (!Objects.isNull(cobCobradorSelected)) {
			this.cobCobrador = cobCobradorSelected;
			cobCobradorSelected = null;
			this.esNuegoRegistro = false;
		}
		this.cobCobradorSelected = cobCobradorSelected;
	}
	
	public boolean isEsNuegoRegistro() {
		return esNuegoRegistro;
	}
	
	public void setEsNuegoRegistro(boolean esNuegoRegistro) {
		this.esNuegoRegistro = esNuegoRegistro;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public BsPersonaService getBsPersonaServiceImpl() {
		return bsPersonaServiceImpl;
	}

	public void setBsPersonaServiceImpl(BsPersonaService bsPersonaServiceImpl) {
		this.bsPersonaServiceImpl = bsPersonaServiceImpl;
	}

	public CobCobradorService getCobCobradorServiceImpl() {
		return cobCobradorServiceImpl;
	}

	public void setCobCobradorServiceImpl(CobCobradorService cobCobradorServiceImpl) {
		this.cobCobradorServiceImpl = cobCobradorServiceImpl;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	public BsPersona getBsPersonaSelected() {
		if(Objects.isNull(bsPersonaSelected)) {
			bsPersonaSelected = new BsPersona();
		}
		return bsPersonaSelected;
	}

	public void setBsPersonaSelected(BsPersona bsPersonaSelected) {
		if (!Objects.isNull(bsPersonaSelected.getId())) {
			this.cobCobrador.setBsPersona(bsPersonaSelected);
			bsPersonaSelected = null;
		}
		
		this.bsPersonaSelected = bsPersonaSelected;
	}

	

	//LAZY
	public LazyDataModel<CobCobrador> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<CobCobrador>((List<CobCobrador>) 
					cobCobradorServiceImpl.buscarCobradorActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<CobCobrador> lazyModel) {
		this.lazyModel = lazyModel;
	}


	public LazyDataModel<BsPersona> getLazyPersonaList() {
		if (Objects.isNull(lazyPersonaList)) {
			lazyPersonaList = new GenericLazyDataModel<BsPersona>(bsPersonaServiceImpl
					.personasSinFichaCobradorPorEmpresaNativo(this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId()));
		}
		return lazyPersonaList;
	}

	public void setLazyPersonaList(LazyDataModel<BsPersona> lazyPersonaList) {
		this.lazyPersonaList = lazyPersonaList;
	}
	

	//METODOS
		public void guardar() {
			if(Objects.isNull(cobCobrador.getBsPersona()) || Objects.isNull(cobCobrador.getBsPersona().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Persona.");
				return;
			}
			try {
				this.cobCobrador.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
				this.cobCobrador.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
				if (!Objects.isNull(cobCobradorServiceImpl.save(this.cobCobrador))) {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
							"El registro se guardo correctamente.");
				} else {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
				}
				this.cleanFields();
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error al Guardar", System.err);
				//e.printStackTrace(System.err);
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
			}
			PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);

		}
		
		public void delete() {
			try {
				if (!Objects.isNull(this.cobCobrador)) {
					this.cobCobradorServiceImpl.deleteById(this.cobCobrador.getId());
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
							"El registro se elimino correctamente.");
				} else {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
				}
				this.cleanFields();
				PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error al eliminar", System.err);
				//e.printStackTrace(System.err);
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
			}

		}
	
	
	
}
