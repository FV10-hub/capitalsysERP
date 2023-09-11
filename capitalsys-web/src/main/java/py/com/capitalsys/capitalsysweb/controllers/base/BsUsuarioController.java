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

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsRol;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.base.BsPersonaService;
import py.com.capitalsys.capitalsysservices.services.base.BsRolService;
import py.com.capitalsys.capitalsysservices.services.base.BsUsuarioService;
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
public class BsUsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// lazy
	private LazyDataModel<BsUsuario> lazyModel;
	private LazyDataModel<BsPersona> lazyPersonaList;
	private LazyDataModel<BsRol> lazyRolList;

	// objetos
	private BsUsuario bsUsuario, bsUsuarioSelected;
	private BsPersona bsPersonaSelected;
	private BsRol bsRolSelected;

	// listas
	private List<String> estadoList;

	private static final String DT_NAME = "dt-usuario";
	private static final String DT_DIALOG_NAME = "manageUsuarioDialog";

	// servicios
	@ManagedProperty("#{bsUsuarioServiceImpl}")
	private BsUsuarioService bsUsuarioServiceImpl;

	@ManagedProperty("#{bsPersonaServiceImpl}")
	private BsPersonaService bsPersonaServiceImpl;

	@ManagedProperty("#{bsRolServiceImpl}")
	private BsRolService bsRolServiceImpl;

	@PostConstruct
	public void init() {
		this.cleanFields();

	}

	public void cleanFields() {
		this.bsUsuario = null;
		this.bsUsuarioSelected = null;
		this.bsPersonaSelected = null;
		this.bsRolSelected = null;

		this.lazyModel = null;
		this.lazyPersonaList = null;
		this.lazyRolList = null;

		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS & SETTERS
	public BsUsuario getBsUsuario() {
		if (Objects.isNull(bsUsuario)) {
			this.bsUsuario = new BsUsuario();
			this.bsUsuario.setBsPersona(new BsPersona());
			this.bsUsuario.setRol(new BsRol());
		}
		return bsUsuario;
	}

	public void setBsUsuario(BsUsuario bsUsuario) {
		this.bsUsuario = bsUsuario;
	}

	public BsUsuario getBsUsuarioSelected() {
		if (Objects.isNull(bsUsuarioSelected)) {
			this.bsUsuarioSelected = new BsUsuario();
			this.bsUsuarioSelected.setBsPersona(new BsPersona());
			this.bsUsuarioSelected.setRol(new BsRol());
		}
		return bsUsuarioSelected;
	}

	public void setBsUsuarioSelected(BsUsuario bsUsuarioSelected) {
		if (!Objects.isNull(bsUsuarioSelected)) {
			this.bsUsuario = bsUsuarioSelected;
		}
		this.bsUsuarioSelected = bsUsuarioSelected;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public BsUsuarioService getBsUsuarioServiceImpl() {
		return bsUsuarioServiceImpl;
	}

	public void setBsUsuarioServiceImpl(BsUsuarioService bsUsuarioServiceImpl) {
		this.bsUsuarioServiceImpl = bsUsuarioServiceImpl;
	}



	public BsPersona getBsPersonaSelected() {
		if(Objects.isNull(bsPersonaSelected)) {
			bsPersonaSelected = new BsPersona();
		}
		return bsPersonaSelected;
	}

	public void setBsPersonaSelected(BsPersona bsPersonaSelected) {
		if (!Objects.isNull(bsPersonaSelected.getId())) {
			this.bsUsuario.setBsPersona(bsPersonaSelected);
			bsPersonaSelected = null;
		}
		
		this.bsPersonaSelected = bsPersonaSelected;
	}

	public BsPersonaService getBsPersonaServiceImpl() {
		return bsPersonaServiceImpl;
	}

	public void setBsPersonaServiceImpl(BsPersonaService bsPersonaServiceImpl) {
		this.bsPersonaServiceImpl = bsPersonaServiceImpl;
	}

	public BsRol getBsRolSelected() {
		if (Objects.isNull(bsRolSelected)) {
			this.bsRolSelected = new BsRol();
		}
		return bsRolSelected;
	}

	public void setBsRolSelected(BsRol bsRolSelected) {
		if (!Objects.isNull(bsRolSelected.getId())) {
			this.bsUsuario.setRol(bsRolSelected);
			bsRolSelected = null;
		}
		this.bsRolSelected = bsRolSelected;
	}

	public BsRolService getBsRolServiceImpl() {
		return bsRolServiceImpl;
	}

	public void setBsRolServiceImpl(BsRolService bsRolServiceImpl) {
		this.bsRolServiceImpl = bsRolServiceImpl;
	}

	// LAZY
	public LazyDataModel<BsUsuario> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsUsuario>((List<BsUsuario>) bsUsuarioServiceImpl.findAll());
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsUsuario> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<BsRol> getLazyRolList() {
		if (Objects.isNull(lazyRolList)) {
			lazyRolList = new GenericLazyDataModel<BsRol>((List<BsRol>) bsRolServiceImpl.findAll());
		}
		return lazyRolList;
	}

	public void setLazyRolList(LazyDataModel<BsRol> lazyRolList) {
		this.lazyRolList = lazyRolList;
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
		try {
			if (!Objects.isNull(bsUsuarioServiceImpl.save(this.bsUsuario))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		PrimeFaces.current().executeScript("PF('" + DT_DIALOG_NAME + "').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsUsuarioSelected)) {
				this.bsUsuarioServiceImpl.deleteById(this.bsUsuarioSelected.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}
	
}