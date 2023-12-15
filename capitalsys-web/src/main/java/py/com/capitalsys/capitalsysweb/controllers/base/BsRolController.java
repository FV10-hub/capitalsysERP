/**
 * 
 */
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

import py.com.capitalsys.capitalsysentities.entities.base.BsRol;
import py.com.capitalsys.capitalsysservices.services.base.BsRolService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/**
 * descomentar si por algun motivo se necesita trabajar directo con spring
 * //@Component y // @Autowired
 */
@ManagedBean
@ViewScoped
//@Component
public class BsRolController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsRolController.class);

	private BsRol bsRol, bsRolSelected;
	private LazyDataModel<BsRol> lazyModel;
	private List<String> estadoList;
	private static final String DT_NAME = "dt-rol";
	private static final String DT_DIALOG_NAME = "manageRolDialog";
	private boolean esModificar;

	@ManagedProperty("#{bsRolServiceImpl}")
	private BsRolService bsRolServiceImpl;
	
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
		this.bsRol = null;
		this.bsRolSelected = null;
		this.lazyModel = null;
		this.esModificar = false;
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS & SETTERS
	public BsRol getBsRol() {
		if (Objects.isNull(bsRol)) {
			this.bsRol = new BsRol();
		}
		return bsRol;
	}

	public void setBsRol(BsRol bsRol) {
		this.bsRol = bsRol;
	}

	public BsRol getBsRolSelected() {
		if (Objects.isNull(bsRolSelected)) {
			this.bsRolSelected = new BsRol();
		}
		return bsRolSelected;
	}

	public void setBsRolSelected(BsRol bsRolSelected) {
		if (!Objects.isNull(bsRolSelected)) {
			this.bsRol = bsRolSelected;
			this.esModificar = true;
		}
		this.bsRolSelected = bsRolSelected;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public LazyDataModel<BsRol> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsRol>((List<BsRol>) bsRolServiceImpl.findAll());
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsRol> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public BsRolService getBsRolServiceImpl() {
		return bsRolServiceImpl;
	}

	public void setBsRolServiceImpl(BsRolService bsRolServiceImpl) {
		this.bsRolServiceImpl = bsRolServiceImpl;
	}

	public boolean isEsModificar() {
		return esModificar;
	}

	public void setEsModificar(boolean esModificar) {
		this.esModificar = esModificar;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	// METODOS
	public void guardar() {
		try {
			this.bsRol.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(bsRolServiceImpl.save(this.bsRol))) {
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
			// e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsRolSelected)) {
				this.bsRolServiceImpl.deleteById(this.bsRolSelected.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al eliminar", System.err);
			// e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

}
