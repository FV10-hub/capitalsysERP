package py.com.capitalsys.capitalsysweb.controllers.ventas.definicion;

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
import py.com.capitalsys.capitalsysentities.entities.ventas.VenCondicionVenta;
import py.com.capitalsys.capitalsysservices.services.ventas.VenCondicionVentaService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 12 dic. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class VenCondicionVentaController {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(VenCondicionVentaController.class);
	
	private VenCondicionVenta venCondicionVenta, venCondicionVentaSelected;
	private LazyDataModel<VenCondicionVenta> lazyModel;

	
	private boolean esNuegoRegistro;

	private List<String> estadoList;

	private static final String DT_NAME = "dt-condicion";
	private static final String DT_DIALOG_NAME = "manageCondicionDialog";

	@ManagedProperty("#{venCondicionVentaServiceImpl}")
	private VenCondicionVentaService venCondicionVentaServiceImpl;
	
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
		this.venCondicionVenta = null;
		this.venCondicionVentaSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	public VenCondicionVenta getVenCondicionVenta() {
		if (Objects.isNull(venCondicionVenta)) {
			this.venCondicionVenta = new VenCondicionVenta();
			this.venCondicionVenta.setEstado(Estado.ACTIVO.getEstado());
			this.venCondicionVenta.setBsEmpresa(new BsEmpresa());
		}
		return venCondicionVenta;
	}

	public void setVenCondicionVenta(VenCondicionVenta venCondicionVenta) {
		this.venCondicionVenta = venCondicionVenta;
	}

	public VenCondicionVenta getVenCondicionVentaSelected() {
		if (Objects.isNull(venCondicionVentaSelected)) {
			this.venCondicionVentaSelected = new VenCondicionVenta();
			this.venCondicionVentaSelected.setBsEmpresa(new BsEmpresa());
		}
		return venCondicionVentaSelected;
	}

	public void setVenCondicionVentaSelected(VenCondicionVenta venCondicionVentaSelected) {
		if (!Objects.isNull(venCondicionVentaSelected)) {
			this.venCondicionVenta = venCondicionVentaSelected;
			this.venCondicionVentaSelected = null;
			this.esNuegoRegistro = false;
		}
		this.venCondicionVentaSelected = venCondicionVentaSelected;
	}

	public LazyDataModel<VenCondicionVenta> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<VenCondicionVenta>((List<VenCondicionVenta>) venCondicionVentaServiceImpl
					.buscarVenCondicionVentaActivosLista(sessionBean.getUsuarioLogueado().getBsEmpresa().getId()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<VenCondicionVenta> lazyModel) {
		this.lazyModel = lazyModel;
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

	public VenCondicionVentaService getVenCondicionVentaServiceImpl() {
		return venCondicionVentaServiceImpl;
	}

	public void setVenCondicionVentaServiceImpl(VenCondicionVentaService venCondicionVentaServiceImpl) {
		this.venCondicionVentaServiceImpl = venCondicionVentaServiceImpl;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	//METODOS
	public void guardar() {
		try {
			this.venCondicionVenta.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
			this.venCondicionVenta.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(venCondicionVentaServiceImpl.save(this.venCondicionVenta))) {
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
					e.getMessage().substring(0, e.getMessage().length()) + "...");
		}
		PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.venCondicionVenta)) {
				this.venCondicionVentaServiceImpl.deleteById(this.venCondicionVenta.getId());
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
					e.getMessage().substring(0, e.getMessage().length()) + "...");
		}

	}
	
	
	
	
	
	
	
	
}
