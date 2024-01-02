package py.com.capitalsys.capitalsysweb.controllers.base.definicion;

import java.time.LocalDate;
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
import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;
import py.com.capitalsys.capitalsysservices.services.base.BsTimbradoService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 02 ene 2024 - Elitebook
*/
@ManagedBean
@ViewScoped
public class BsTimbradoController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsTimbradoController.class);

	private BsTimbrado bsTimbrado, bsTimbradoSelected;
	private LazyDataModel<BsTimbrado> lazyModel;

	private boolean esNuegoRegistro;

	private List<String> estadoList;

	private static final String DT_NAME = "dt-timbrado";
	private static final String DT_DIALOG_NAME = "manageTimbradoDialog";

	@ManagedProperty("#{bsTimbradoServiceImpl}")
	private BsTimbradoService bsTimbradoServiceImpl;

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
		this.bsTimbrado = null;
		this.bsTimbradoSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public BsTimbrado getBsTimbrado() {
		if (Objects.isNull(bsTimbrado)) {
			this.bsTimbrado = new BsTimbrado();
			this.bsTimbrado.setFechaVigenciaDesde(LocalDate.now());
			this.bsTimbrado.setFechaVigenciaHasta(LocalDate.now());
			this.bsTimbrado.setBsEmpresa(new BsEmpresa());
			this.bsTimbrado.setEstado(Estado.ACTIVO.getEstado());
		}
		return bsTimbrado;
	}

	public void setBsTimbrado(BsTimbrado bsTimbrado) {
		this.bsTimbrado = bsTimbrado;
	}

	public BsTimbrado getBsTimbradoSelected() {
		if (Objects.isNull(bsTimbradoSelected)) {
			this.bsTimbradoSelected = new BsTimbrado();
			this.bsTimbradoSelected.setBsEmpresa(new BsEmpresa());
			this.bsTimbradoSelected.setEstado(Estado.ACTIVO.getEstado());
		}
		return bsTimbradoSelected;
	}

	public void setBsTimbradoSelected(BsTimbrado bsTimbradoSelected) {
		if (!Objects.isNull(bsTimbradoSelected)) {
			this.bsTimbrado = bsTimbradoSelected;
			bsTimbradoSelected = null;
			this.esNuegoRegistro = false;
		}
		this.bsTimbradoSelected = bsTimbradoSelected;
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

	public BsTimbradoService getBsTimbradoServiceImpl() {
		return bsTimbradoServiceImpl;
	}

	public void setBsTimbradoServiceImpl(BsTimbradoService bsTimbradoServiceImpl) {
		this.bsTimbradoServiceImpl = bsTimbradoServiceImpl;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	// LAZY
	public LazyDataModel<BsTimbrado> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsTimbrado>((List<BsTimbrado>) bsTimbradoServiceImpl
					.buscarBsTimbradoActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsTimbrado> lazyModel) {
		this.lazyModel = lazyModel;
	}

	// METODOS
	public void guardar() {
		try {
			this.bsTimbrado.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
			this.bsTimbrado.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(bsTimbradoServiceImpl.save(this.bsTimbrado))) {
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
					e.getMessage().length() + "...");
		}

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsTimbrado)) {
				this.bsTimbradoServiceImpl.deleteById(this.bsTimbrado.getId());
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
					e.getMessage().length() + "...");
		}

	}

}
