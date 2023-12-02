package py.com.capitalsys.capitalsysweb.controllers.cobranzas;

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
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCliente;
import py.com.capitalsys.capitalsysservices.services.base.BsPersonaService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobClienteService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 30 nov. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class CobClienteController {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(CobClienteController.class);

	private CobCliente cobCliente, cobClienteSelected;
	private LazyDataModel<CobCliente> lazyModel;
	private LazyDataModel<BsPersona> lazyPersonaList;

	private BsPersona bsPersonaSelected;
	private boolean esNuegoRegistro;

	private List<String> estadoList;

	private static final String DT_NAME = "dt-cliente";
	private static final String DT_DIALOG_NAME = "manageClienteDialog";

	@ManagedProperty("#{bsPersonaServiceImpl}")
	private BsPersonaService bsPersonaServiceImpl;

	@ManagedProperty("#{cobClienteServiceImpl}")
	private CobClienteService cobClienteServiceImpl;

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
		this.cobCliente = null;
		this.cobClienteSelected = null;
		this.bsPersonaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;
		this.lazyPersonaList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public CobCliente getCobCliente() {
		if (Objects.isNull(cobCliente)) {
			this.cobCliente = new CobCliente();
			this.cobCliente.setBsEmpresa(new BsEmpresa());
			this.cobCliente.setBsPersona(new BsPersona());
		}
		return cobCliente;
	}

	public void setCobCliente(CobCliente cobCliente) {
		this.cobCliente = cobCliente;
	}

	public CobCliente getCobClienteSelected() {
		if (Objects.isNull(cobClienteSelected)) {
			this.cobClienteSelected = new CobCliente();
			this.cobClienteSelected.setBsEmpresa(new BsEmpresa());
			this.cobClienteSelected.setBsPersona(new BsPersona());
		}
		return cobClienteSelected;
	}

	public void setCobClienteSelected(CobCliente cobClienteSelected) {
		if (!Objects.isNull(cobClienteSelected)) {
			this.cobCliente = cobClienteSelected;
			cobClienteSelected = null;
			this.esNuegoRegistro = false;
		}
		this.cobClienteSelected = cobClienteSelected;
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

	public CobClienteService getCobClienteServiceImpl() {
		return cobClienteServiceImpl;
	}

	public void setCobClienteServiceImpl(CobClienteService cobClienteServiceImpl) {
		this.cobClienteServiceImpl = cobClienteServiceImpl;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public BsPersona getBsPersonaSelected() {
		if (Objects.isNull(bsPersonaSelected)) {
			bsPersonaSelected = new BsPersona();
		}
		return bsPersonaSelected;
	}

	public void setBsPersonaSelected(BsPersona bsPersonaSelected) {
		if (!Objects.isNull(bsPersonaSelected.getId())) {
			this.cobCliente.setBsPersona(bsPersonaSelected);
			bsPersonaSelected = null;
		}

		this.bsPersonaSelected = bsPersonaSelected;
	}

	// LAZY
	public LazyDataModel<CobCliente> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<CobCliente>((List<CobCliente>) cobClienteServiceImpl
					.buscarClienteActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<CobCliente> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<BsPersona> getLazyPersonaList() {
		if (Objects.isNull(lazyPersonaList)) {
			lazyPersonaList = new GenericLazyDataModel<BsPersona>(bsPersonaServiceImpl
					.personasSinFichaClientePorEmpresa(this.sessionBean.getUsuarioLogueado().getBsEmpresa().getId()));
		}
		return lazyPersonaList;
	}

	public void setLazyPersonaList(LazyDataModel<BsPersona> lazyPersonaList) {
		this.lazyPersonaList = lazyPersonaList;
	}

	// METODOS
	public void guardar() {
		if (Objects.isNull(cobCliente.getBsPersona()) || Objects.isNull(cobCliente.getBsPersona().getId())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Persona.");
			return;
		}
		try {
			this.cobCliente.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
			if (!Objects.isNull(cobClienteServiceImpl.save(this.cobCliente))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			//e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}
		PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.cobCliente)) {
				this.cobClienteServiceImpl.deleteById(this.cobCliente.getId());
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
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

}
