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

import py.com.capitalsys.capitalsysentities.entities.base.BsMoneda;
import py.com.capitalsys.capitalsysservices.services.base.BsMonedaService;
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
public class BsMonedaController implements Serializable {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsMonedaController.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// lazy
	private LazyDataModel<BsMoneda> lazyModel;

	// objetos
	private BsMoneda bsMoneda, bsMonedaSelected;
	private boolean esNuegoRegistro;

	// listas
	private List<String> estadoList;

	private static final String DT_NAME = "dt-moneda";
	private static final String DT_DIALOG_NAME = "manageMonedaDialog";

	// servicios
	@ManagedProperty("#{bsMonedaServiceImpl}")
	private BsMonedaService bsMonedaServiceImpl;

	@PostConstruct
	public void init() {
		this.cleanFields();

	}

	public void cleanFields() {
		this.bsMoneda = null;
		this.bsMonedaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS & SETTERS
	public BsMoneda getBsMoneda() {
		if (Objects.isNull(bsMoneda)) {
			this.bsMoneda = new BsMoneda();
		}
		return bsMoneda;
	}
	
	public void setBsMoneda(BsMoneda bsMoneda) {
		this.bsMoneda = bsMoneda;
	}
	
	public BsMoneda getBsMonedaSelected() {
		if (Objects.isNull(bsMonedaSelected)) {
			this.bsMonedaSelected = new BsMoneda();
		}
		return bsMonedaSelected;
	}
	
	public void setBsMonedaSelected(BsMoneda bsMonedaSelected) {
		if (!Objects.isNull(bsMonedaSelected)) {
			this.bsMoneda = bsMonedaSelected;
			this.esNuegoRegistro = false;
		}
		this.bsMonedaSelected = bsMonedaSelected;
	}

	public BsMonedaService getBsMonedaServiceImpl() {
		return bsMonedaServiceImpl;
	}

	public void setBsMonedaServiceImpl(BsMonedaService bsMonedaServiceImpl) {
		this.bsMonedaServiceImpl = bsMonedaServiceImpl;
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

	// LAZY
	public LazyDataModel<BsMoneda> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsMoneda>((List<BsMoneda>) bsMonedaServiceImpl.findAll());
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsMoneda> lazyModel) {
		this.lazyModel = lazyModel;
	}


	// METODOS
	public void guardar() {
		try {
			if (!Objects.isNull(bsMonedaServiceImpl.save(this.bsMoneda))) {
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
			//e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
		}
		

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsMonedaSelected)) {
				this.bsMonedaServiceImpl.deleteById(this.bsMonedaSelected.getId());
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
