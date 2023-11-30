package py.com.capitalsys.capitalsysweb.controllers.base;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysentities.entities.base.BsParametro;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysservices.services.base.BsEmpresaService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.base.BsTipoValorService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/*
* 30 nov. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class BsTipoValorController {

	private BsTipoValor bsTipoValor, bsTipoValorSelected;
	private LazyDataModel<BsTipoValor> lazyModel;
	private LazyDataModel<BsModulo> lazyModuloList;
	
	private BsModulo bsModuloSelected;
	private boolean esNuegoRegistro;
	
	private List<String> estadoList;

	private static final String DT_NAME = "dt-tipovalor";
	private static final String DT_DIALOG_NAME = "manageTipoValorDialog";
		
	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	@ManagedProperty("#{bsTipoValorServiceImpl}")
	private BsTipoValorService bsTipoValorServiceImpl;
	
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
		this.bsTipoValor = null;
		this.bsTipoValorSelected = null;
		this.bsModuloSelected = null;
		this.esNuegoRegistro = true;

		this.lazyModel = null;
		this.lazyModuloList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	//GETTERS Y SETTERS
	public BsTipoValor getBsTipoValor() {
		if (Objects.isNull(bsTipoValor)) {
			this.bsTipoValor = new BsTipoValor();
			this.bsTipoValor.setBsEmpresa(new BsEmpresa());
			this.bsTipoValor.setBsModulo(new BsModulo());
		}
		return bsTipoValor;
	}

	public void setBsTipoValor(BsTipoValor bsTipoValor) {
		this.bsTipoValor = bsTipoValor;
	}

	public BsTipoValor getBsTipoValorSelected() {
		if (Objects.isNull(bsTipoValorSelected)) {
			this.bsTipoValorSelected = new BsTipoValor();
			this.bsTipoValorSelected.setBsEmpresa(new BsEmpresa());
			this.bsTipoValorSelected.setBsModulo(new BsModulo());
		}
		return bsTipoValorSelected;
	}

	public void setBsTipoValorSelected(BsTipoValor bsTipoValorSelected) {
		if (!Objects.isNull(bsTipoValorSelected)) {
			this.bsTipoValor = bsTipoValorSelected;
			bsTipoValorSelected = null;
			this.esNuegoRegistro = false;
		}
		this.bsTipoValorSelected = bsTipoValorSelected;
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

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public BsTipoValorService getBsTipoValorServiceImpl() {
		return bsTipoValorServiceImpl;
	}

	public void setBsTipoValorServiceImpl(BsTipoValorService bsTipoValorServiceImpl) {
		this.bsTipoValorServiceImpl = bsTipoValorServiceImpl;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	public BsModulo getBsModuloSelected() {
		if(Objects.isNull(bsModuloSelected)) {
			bsModuloSelected = new BsModulo();
		}
		return bsModuloSelected;
	}

	public void setBsModuloSelected(BsModulo bsModuloSelected) {
		if (!Objects.isNull(bsModuloSelected.getId())) {
			this.bsTipoValor.setBsModulo(bsModuloSelected);
			bsModuloSelected = null;
		}
		this.bsModuloSelected = bsModuloSelected;
	}

	

	//LAZY
	public LazyDataModel<BsTipoValor> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsTipoValor>((List<BsTipoValor>) 
					bsTipoValorServiceImpl.buscarTipoValorActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsTipoValor> lazyModel) {
		this.lazyModel = lazyModel;
	}


	public LazyDataModel<BsModulo> getLazyModuloList() {
		if (Objects.isNull(lazyModuloList)) {
			lazyModuloList = new GenericLazyDataModel<BsModulo>((List<BsModulo>) bsModuloServiceImpl.buscarModulosActivosLista());
		}
		return lazyModuloList;
	}

	public void setLazyModuloList(LazyDataModel<BsModulo> lazyModuloList) {
		this.lazyModuloList = lazyModuloList;
	}
	

	//METODOS
		public void guardar() {
			if(Objects.isNull(bsTipoValor.getBsModulo()) && Objects.isNull(bsTipoValor.getBsModulo().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Modulo.");
				return;
			}
			try {
				this.bsTipoValor.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
				if (!Objects.isNull(bsTipoValorServiceImpl.save(this.bsTipoValor))) {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
							"El registro se guardo correctamente.");
				} else {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
				}
				this.cleanFields();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
			}
			PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);

		}
		
		public void delete() {
			try {
				if (!Objects.isNull(this.bsTipoValor)) {
					this.bsTipoValorServiceImpl.deleteById(this.bsTipoValor.getId());
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
							"El registro se elimino correctamente.");
				} else {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
				}
				this.cleanFields();
				PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
			}

		}
	
	
	
}
