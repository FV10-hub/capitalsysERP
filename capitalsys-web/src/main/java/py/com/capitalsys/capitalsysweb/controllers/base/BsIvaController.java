/**
 * 
 */
package py.com.capitalsys.capitalsysweb.controllers.base;

import java.io.Serializable;
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

import py.com.capitalsys.capitalsysentities.entities.base.BsIva;
import py.com.capitalsys.capitalsysservices.services.base.BsIvaService;
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
public class BsIvaController implements Serializable {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsIvaController.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// lazy
	private LazyDataModel<BsIva> lazyModel;

	// objetos
	private BsIva bsIva, bsIvaSelected;
	private boolean esNuegoRegistro;

	// listas
	private List<String> estadoList;

	private static final String DT_NAME = "dt-iva";
	private static final String DT_DIALOG_NAME = "manageIvaDialog";

	// servicios
	@ManagedProperty("#{bsIvaServiceImpl}")
	private BsIvaService bsIvaServiceImpl;
	
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
		this.bsIva = null;
		this.bsIvaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS & SETTERS
	public BsIva getBsIva() {
		if (Objects.isNull(bsIva)) {
			this.bsIva = new BsIva();
		}
		return bsIva;
	}

	public void setBsIva(BsIva bsIva) {
		this.bsIva = bsIva;
	}

	public BsIva getBsIvaSelected() {
		if (Objects.isNull(bsIvaSelected)) {
			this.bsIvaSelected = new BsIva();
		}
		return bsIvaSelected;
	}

	public void setBsIvaSelected(BsIva bsIvaSelected) {
		if (!Objects.isNull(bsIvaSelected)) {
			this.bsIva = bsIvaSelected;
			this.esNuegoRegistro = false;
		}
		this.bsIvaSelected = bsIvaSelected;
	}

	public BsIvaService getBsIvaServiceImpl() {
		return bsIvaServiceImpl;
	}

	public void setBsIvaServiceImpl(BsIvaService bsIvaServiceImpl) {
		this.bsIvaServiceImpl = bsIvaServiceImpl;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public boolean isEsNuegoRegistro() {
		return esNuegoRegistro;
	}

	public void setEsNuegoRegistro(boolean esNuegoRegistro) {
		this.esNuegoRegistro = esNuegoRegistro;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	// LAZY
	public LazyDataModel<BsIva> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsIva>((List<BsIva>) bsIvaServiceImpl.findAll());
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsIva> lazyModel) {
		this.lazyModel = lazyModel;
	}


	// METODOS
	public void guardar() {
		try {
			this.bsIva.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(bsIvaServiceImpl.save(this.bsIva))) {
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
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
		}
		

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsIvaSelected)) {
				this.bsIvaServiceImpl.deleteById(this.bsIvaSelected.getId());
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
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
		}

	}
	
}
