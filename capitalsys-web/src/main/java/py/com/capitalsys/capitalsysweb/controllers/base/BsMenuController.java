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

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysservices.services.base.BsMenuService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/**
 * descomentar si por algun motivo se necesita trabajar directo con spring //@Component y // @Autowired
 */
@ManagedBean
@ViewScoped
//@Component
public class BsMenuController {

	private BsMenu bsMenu, bsMenuSelected;
	private LazyDataModel<BsMenu> lazyModel;
	private List<BsModulo> lazyModelModulo;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{bsMenuServiceImpl}")
	// @Autowired
	private BsMenuService bsMenuServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	@PostConstruct
	public void init() {
		this.cleanFields();

	}
	
	public void cleanFields() {
		this.bsMenu = null;
		this.bsMenuSelected = null;
		this.lazyModel = null;
		this.lazyModelModulo = null;
	}

	// GETTERS & SETTERS
	public BsMenu getBsMenu() {

		if (Objects.isNull(bsMenu)) {
			this.bsMenu = new BsMenu();
			this.bsMenu.setBsModulo(new BsModulo());
		}
		return bsMenu;
	}

	public void setBsMenu(BsMenu bsMenu) {
		this.bsMenu = bsMenu;
	}

	public BsMenu getBsMenuSelected() {

		if (Objects.isNull(bsMenuSelected)) {
			this.bsMenuSelected = new BsMenu();
			this.bsMenuSelected.setBsModulo(new BsModulo());
		}

		return bsMenuSelected;
	}

	public void setBsMenuSelected(BsMenu bsMenuSelected) {
		if (!Objects.isNull(bsMenuSelected)) {
			this.bsMenu = bsMenuSelected;
			System.out.println("PASO POR SELECTED");
		}
		this.bsMenuSelected = bsMenuSelected;
	}

	public BsMenuService getBsMenuServiceImpl() {
		return bsMenuServiceImpl;
	}

	public void setBsMenuServiceImpl(BsMenuService bsMenuServiceImpl) {
		this.bsMenuServiceImpl = bsMenuServiceImpl;
	}

	public LazyDataModel<BsMenu> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsMenu>(bsMenuServiceImpl.buscarTodosLista());
			System.out.println("PASO POR LAZY DATAMODEL");
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsMenu> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public List<BsModulo> getLazyModelModulo() {
		if (Objects.isNull(lazyModelModulo)) {
			lazyModelModulo = this.bsModuloServiceImpl.buscarTodosLista();
			System.out.println("PASO POR LAZYMODULO");
		}

		return lazyModelModulo;
	}

	public void setLazyModelModulo(List<BsModulo> lazyModelModulo) {
		this.lazyModelModulo = lazyModelModulo;
	}

	// METODOS
	public void guardar() {
		try {
			if (!Objects.isNull(bsMenuServiceImpl.guardar(bsMenu))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		PrimeFaces.current().executeScript("PF('manageMenutDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-menu");

	}
	
	public void deleteMenu() {
        try {
			if (!Objects.isNull(this.bsMenuSelected)) {
				this.bsMenuServiceImpl.eliminar(this.bsMenuSelected.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:dt-menu");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
       
	}

}
