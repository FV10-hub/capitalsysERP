package py.com.capitalsys.capitalsysweb.controllers.base.definicion;

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
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysentities.entities.base.BsParametro;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysservices.services.base.BsEmpresaService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 24 nov. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class BsParametroController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsParametroController.class);

	private BsParametro bsParametro, bsParametroSelected;
	private LazyDataModel<BsParametro> lazyModel;
	private LazyDataModel<BsEmpresa> lazyEmpresaList;
	private LazyDataModel<BsModulo> lazyModuloList;
	private BsEmpresa bsEmpresaSelected;
	private BsModulo bsModuloSelected;
	private boolean esNuegoRegistro;

	private List<String> estadoList;

	private static final String DT_NAME = "dt-parametro";
	private static final String DT_DIALOG_NAME = "manageParametroDialog";

	@ManagedProperty("#{bsEmpresaServiceImpl}")
	private BsEmpresaService bsEmpresaServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	@ManagedProperty("#{bsParametroServiceImpl}")
	private BsParametroService bsParametroServiceImpl;

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
		this.bsParametro = null;
		this.bsParametroSelected = null;
		this.bsEmpresaSelected = null;
		this.bsModuloSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;
		this.lazyEmpresaList = null;
		this.lazyModuloList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public BsParametro getBsParametro() {
		if (Objects.isNull(bsParametro)) {
			this.bsParametro = new BsParametro();
			this.bsParametro.setBsEmpresa(new BsEmpresa());
			this.bsParametro.setBsModulo(new BsModulo());
		}
		return bsParametro;
	}

	public void setBsParametro(BsParametro bsParametro) {
		this.bsParametro = bsParametro;
	}

	public BsParametro getBsParametroSelected() {
		if (Objects.isNull(bsParametroSelected)) {
			this.bsParametroSelected = new BsParametro();
			this.bsParametroSelected.setBsEmpresa(new BsEmpresa());
			this.bsParametroSelected.setBsModulo(new BsModulo());
		}
		return bsParametroSelected;
	}

	public void setBsParametroSelected(BsParametro bsParametroSelected) {
		if (!Objects.isNull(bsParametroSelected)) {
			this.bsParametro = bsParametroSelected;
			bsParametroSelected = null;
			this.esNuegoRegistro = false;
		}
		this.bsParametroSelected = bsParametroSelected;
	}

	public BsEmpresa getBsEmpresaSelected() {
		if (Objects.isNull(bsEmpresaSelected)) {
			this.bsEmpresaSelected = new BsEmpresa();
			this.bsEmpresaSelected.setBsPersona(new BsPersona());
		}
		return bsEmpresaSelected;
	}

	public void setBsEmpresaSelected(BsEmpresa bsEmpresaSelected) {
		if (!Objects.isNull(bsEmpresaSelected.getId())) {
			this.bsParametro.setBsEmpresa(bsEmpresaSelected);
			bsEmpresaSelected = null;
		}
		this.bsEmpresaSelected = bsEmpresaSelected;
	}

	public BsModulo getBsModuloSelected() {
		if (Objects.isNull(bsModuloSelected)) {
			bsModuloSelected = new BsModulo();
		}
		return bsModuloSelected;
	}

	public void setBsModuloSelected(BsModulo bsModuloSelected) {
		if (!Objects.isNull(bsModuloSelected.getId())) {
			this.bsParametro.setBsModulo(bsModuloSelected);
			bsModuloSelected = null;
		}
		this.bsModuloSelected = bsModuloSelected;
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

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public BsParametroService getBsParametroServiceImpl() {
		return bsParametroServiceImpl;
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

	// lazy
	public LazyDataModel<BsParametro> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsParametro>((List<BsParametro>) bsParametroServiceImpl.findAll());
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsParametro> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<BsEmpresa> getLazyEmpresaList() {
		if (Objects.isNull(lazyEmpresaList)) {
			lazyEmpresaList = new GenericLazyDataModel<BsEmpresa>((List<BsEmpresa>) bsEmpresaServiceImpl.findAll());
		}
		return lazyEmpresaList;
	}

	public void setLazyEmpresaList(LazyDataModel<BsEmpresa> lazyEmpresaList) {
		this.lazyEmpresaList = lazyEmpresaList;
	}

	public LazyDataModel<BsModulo> getLazyModuloList() {
		if (Objects.isNull(lazyModuloList)) {
			lazyModuloList = new GenericLazyDataModel<BsModulo>(
					(List<BsModulo>) bsModuloServiceImpl.buscarTodosLista());
		}
		return lazyModuloList;
	}

	public void setLazyModuloList(LazyDataModel<BsModulo> lazyModuloList) {
		this.lazyModuloList = lazyModuloList;
	}

	// METODOS
	public void guardar() {
		if (Objects.isNull(bsParametro.getBsEmpresa()) || Objects.isNull(bsParametro.getBsEmpresa().getId())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Empresa.");
			return;
		}
		if (Objects.isNull(bsParametro.getBsModulo()) || Objects.isNull(bsParametro.getBsModulo().getId())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Modulo.");
			return;
		}
		try {
			this.bsParametro.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			if (!Objects.isNull(bsParametroServiceImpl.save(this.bsParametro))) {
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
			if (!Objects.isNull(this.bsParametro)) {
				this.bsParametroServiceImpl.deleteById(this.bsParametro.getId());
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
