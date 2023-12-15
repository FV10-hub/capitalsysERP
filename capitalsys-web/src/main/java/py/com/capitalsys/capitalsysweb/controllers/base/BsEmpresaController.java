package py.com.capitalsys.capitalsysweb.controllers.base;

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
import py.com.capitalsys.capitalsysservices.services.base.BsEmpresaService;
import py.com.capitalsys.capitalsysservices.services.base.BsPersonaService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 24 nov. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class BsEmpresaController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsEmpresaController.class);

	private BsEmpresa bsEmpresa, bsEmpresaSelected;
	private LazyDataModel<BsEmpresa> lazyModel;
	private LazyDataModel<BsPersona> lazyPersonaList;
	private BsPersona personaSelected;
	private boolean esNuegoRegistro;

	private List<String> estadoList;

	private static final String DT_NAME = "dt-empresa";
	private static final String DT_DIALOG_NAME = "manageEmpresaDialog";

	@ManagedProperty("#{bsEmpresaServiceImpl}")
	private BsEmpresaService bsEmpresaServiceImpl;

	@ManagedProperty("#{bsPersonaServiceImpl}")
	private BsPersonaService bsPersonaServiceImpl;
	
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
		this.bsEmpresa = null;
		this.bsEmpresaSelected = null;
		this.personaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;
		this.lazyPersonaList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public BsEmpresa getBsEmpresa() {
		if (Objects.isNull(bsEmpresa)) {
			this.bsEmpresa = new BsEmpresa();
			this.bsEmpresa.setBsPersona(new BsPersona());
		}
		return bsEmpresa;
	}

	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}

	public BsEmpresa getBsEmpresaSelected() {
		if (Objects.isNull(bsEmpresaSelected)) {
			this.bsEmpresaSelected = new BsEmpresa();
			this.bsEmpresaSelected.setBsPersona(new BsPersona());
		}
		return bsEmpresaSelected;
	}

	public void setBsEmpresaSelected(BsEmpresa bsEmpresaSelected) {
		if (!Objects.isNull(bsEmpresaSelected)) {
			this.bsEmpresa = bsEmpresaSelected;
			this.esNuegoRegistro = false;
		}
		this.bsEmpresaSelected = bsEmpresaSelected;
	}

	public BsPersona getPersonaSelected() {
		if (Objects.isNull(personaSelected)) {
			personaSelected = new BsPersona();
		}
		return personaSelected;
	}

	public void setPersonaSelected(BsPersona personaSelected) {
		if (!Objects.isNull(personaSelected.getId())) {
			this.bsEmpresa.setBsPersona(personaSelected);
			personaSelected = null;
		}
		this.personaSelected = personaSelected;
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

	public BsEmpresaService getBsEmpresaServiceImpl() {
		return bsEmpresaServiceImpl;
	}

	public void setBsEmpresaServiceImpl(BsEmpresaService bsEmpresaServiceImpl) {
		this.bsEmpresaServiceImpl = bsEmpresaServiceImpl;
	}

	public BsPersonaService getBsPersonaServiceImpl() {
		return bsPersonaServiceImpl;
	}

	public void setBsPersonaServiceImpl(BsPersonaService bsPersonaServiceImpl) {
		this.bsPersonaServiceImpl = bsPersonaServiceImpl;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	// lazy
	public LazyDataModel<BsEmpresa> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsEmpresa>((List<BsEmpresa>) bsEmpresaServiceImpl.findAll());
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsEmpresa> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<BsPersona> getLazyPersonaList() {
		if (Objects.isNull(lazyPersonaList)) {
			lazyPersonaList = new GenericLazyDataModel<BsPersona>(bsPersonaServiceImpl.buscarTodosLista());
		}
		return lazyPersonaList;
	}

	public void setLazyPersonaList(LazyDataModel<BsPersona> lazyPersonaList) {
		this.lazyPersonaList = lazyPersonaList;
	}

	// METODOS
	public void guardar() {
		if (Objects.isNull(bsEmpresa.getBsPersona()) || Objects.isNull(bsEmpresa.getBsPersona().getId())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Persona.");
			return;
		}
		try {
			this.bsEmpresa.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(bsEmpresaServiceImpl.save(this.bsEmpresa))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsEmpresaSelected)) {
				this.bsEmpresaServiceImpl.deleteById(this.bsEmpresaSelected.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al eliminar", System.err);
			e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

}
