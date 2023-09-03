/**
 * 
 */
package py.com.capitalsys.capitalsysweb.controllers.base;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysservices.services.base.BsMenuService;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/**
 * 
 */
@ManagedBean
@ViewScoped
//@Component
public class BsMenuController {
		
	private BsMenu bsMenu, bsMenuSelected;
	private LazyDataModel<BsMenu> lazyModel;
	
	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{bsMenuServiceImpl}")
	//@Autowired
	private BsMenuService bsMenuServiceImpl;
	
	
	@PostConstruct
    public void init() {
		lazyModel = new GenericLazyDataModel<BsMenu>(bsMenuServiceImpl.buscarTodosLista());
	}

	
	//GETTERS & SETTERS
	public BsMenu getBsMenu() {
		if(Objects.isNull(bsMenu)) {
			this.bsMenu = new BsMenu();
			this.bsMenu.setBsModulo(new BsModulo());
		}
		return bsMenu;
	}


	public void setBsMenu(BsMenu bsMenu) {
		this.bsMenu = bsMenu;
	}


	public BsMenu getBsMenuSelected() {
		if(Objects.isNull(bsMenuSelected)) {
			this.bsMenuSelected = new BsMenu();
			this.bsMenuSelected.setBsModulo(new BsModulo());
		}
		return bsMenuSelected;
	}


	public void setBsMenuSelected(BsMenu bsMenuSelected) {
		this.bsMenuSelected = bsMenuSelected;
	}

	public BsMenuService getBsMenuServiceImpl() {
		return bsMenuServiceImpl;
	}

	public void setBsMenuServiceImpl(BsMenuService bsMenuServiceImpl) {
		this.bsMenuServiceImpl = bsMenuServiceImpl;
	}

	public LazyDataModel<BsMenu> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsMenu> lazyModel) {
		this.lazyModel = lazyModel;
	}
	
	
	
	
	
}
