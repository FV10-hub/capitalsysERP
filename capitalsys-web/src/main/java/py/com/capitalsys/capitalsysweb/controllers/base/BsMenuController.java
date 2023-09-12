/**
 * 
 */
package py.com.capitalsys.capitalsysweb.controllers.base;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.collections4.CollectionUtils;
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
 * descomentar si por algun motivo se necesita trabajar directo con spring
 * //@Component y // @Autowired
 */
@ManagedBean
@ViewScoped
//@Component
public class BsMenuController {

	private BsMenu bsMenu, bsMenuSelected;
	private LazyDataModel<BsMenu> lazyModel;
	private List<BsModulo> lazyModelModulo;
	private List<String> tipoList;
	private List<String> tipoListAgrupador;
	private List<BsMenu> subMenuList;
	private boolean isSubmenu;
	private String tipoMenu;
	private boolean esNuegoRegistro;

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
		this.isSubmenu = true;
		this.tipoMenu = null;
		this.esNuegoRegistro = true;
		this.tipoList = List.of("SUBMENU", "ITEM");
		this.tipoListAgrupador  = List.of("DEFINICION", "MOVIMIENTO", "REPORTE");
		this.subMenuList = null;
	}

	// GETTERS & SETTERS
	public BsMenu getBsMenu() {

		if (Objects.isNull(bsMenu)) {
			this.bsMenu = new BsMenu();
			this.bsMenu.setBsModulo(new BsModulo());
			//this.bsMenu.setSubMenuPadre(new BsMenu());
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
			//this.bsMenuSelected.setSubMenuPadre(new BsMenu());
		}

		return bsMenuSelected;
	}

	public void setBsMenuSelected(BsMenu bsMenuSelected) {
		if (!Objects.isNull(bsMenuSelected)) {
			this.bsMenu = bsMenuSelected;
			this.esNuegoRegistro = false;
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
		}

		return lazyModelModulo;
	}

	public void setLazyModelModulo(List<BsModulo> lazyModelModulo) {
		this.lazyModelModulo = lazyModelModulo;
	}

	public List<String> getTipoList() {
		return tipoList;
	}

	public void setTipoList(List<String> tipoList) {
		this.tipoList = tipoList;
	}

	public List<BsMenu> getSubMenuList() {
		if (!Objects.isNull(this.bsMenu.getBsModulo().getId())) {
			if (CollectionUtils.isEmpty(subMenuList)) {
				subMenuList = bsMenuServiceImpl.buscarTodosLista().stream()
						.filter(menu -> this.bsMenu.getBsModulo().getId() == menu.getBsModulo().getId())
						.collect(Collectors.toList());
			}
		} else {
			subMenuList = bsMenuServiceImpl.buscarTodosLista();
		}

		return subMenuList;
	}

	public void setSubMenuList(List<BsMenu> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public boolean getIsSubmenu() {
		return isSubmenu;
	}

	public void setIsSubmenu(boolean isSubmenu) {
		this.isSubmenu = isSubmenu;
	}

	public String getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(String tipoMenu) {
		if ("SUBMENU".equalsIgnoreCase(tipoMenu)) {
			this.isSubmenu = false;
		} else {
			this.isSubmenu = true;
		}
		this.bsMenu.setTipoMenu(tipoMenu);
		this.tipoMenu = tipoMenu;
	}

	
	public List<String> getTipoListAgrupador() {
		return tipoListAgrupador;
	}

	public void setTipoListAgrupador(List<String> tipoListAgrupador) {
		this.tipoListAgrupador = tipoListAgrupador;
	}

	public boolean isEsNuegoRegistro() {
		return esNuegoRegistro;
	}

	public void setEsNuegoRegistro(boolean esNuegoRegistro) {
		this.esNuegoRegistro = esNuegoRegistro;
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
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
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
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getCause().getMessage().substring(0, 50)+"...");
		}

	}

	public void onRowSelectSubMenu(SelectEvent<BsMenu> event) {
		if (!Objects.isNull(event.getObject())) {
			this.bsMenu.setSubMenuPadre(event.getObject());
			this.bsMenuSelected = null;
			PrimeFaces.current().ajax().update("form:manage-menu");
			PrimeFaces.current().executeScript("PF('dlgSubMenus').hide()");

		}

	}

	public void onModuloChange() {
		this.getSubMenuList();

	}

}
