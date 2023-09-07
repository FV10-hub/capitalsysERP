/**
 * 
 */
package py.com.capitalsys.capitalsysweb.session;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.LoginService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;

/**
 * @author DevPredator Clase que mantendra la informacion en la sesion del
 *         usuario.
 */
@ManagedBean
@SessionScoped
public class MenuBean {

	private MenuModel modulosPrime;

	// Objetos personalizado
	private List<BsModulo> moduloList;
	private List<BsMenu> listaItems;
	private List<BsMenu> itemsSubMenu;
	private List<MenuDto> menuListFromDB;
	private BsUsuario usuarioLogueado;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{loginServiceImpl}")
	private LoginService loginServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	private MenuModel model;

	private void ListarMenu(BsUsuario user) {
		try {
			this.menuListFromDB = this.loginServiceImpl.consultarMenuPorUsuario(user.getId());
			this.moduloList = this.bsModuloServiceImpl.buscarTodosLista();
			if (CollectionUtils.isNotEmpty(menuListFromDB) && CollectionUtils.isNotEmpty(moduloList)) {
				this.construirMenu();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// "SUBMENU", "ITEM"
	private void construirMenu() {
		this.model = new DefaultMenuModel();
		this.moduloList.stream().forEach(modulo -> {
			DefaultSubMenu subMenuModulo = DefaultSubMenu.builder().label(modulo.getNombre()).icon("pi pi-fw pi-cog")
					.build();
			this.model.getElements().add(subMenuModulo);
			
			this.menuListFromDB.stream()
			.filter(menmod -> menmod.getMenuItem().getBsModulo().getId() == modulo.getId())
			.forEach(menudtoFiltered -> {
				DefaultSubMenu segundoSubmenu;
	            DefaultSubMenu tercerSubmenu;
	            DefaultMenuItem item;
				segundoSubmenu = DefaultSubMenu.builder().label(menudtoFiltered.getMenuItem().getTitulo()).build();
                    if (menudtoFiltered.getMenuItem().getIdMenuItem() == null) {
                        item = DefaultMenuItem.builder()
                        		.title(menudtoFiltered.getMenuItem().getBsMenu().getNombre())
                        		.url(menudtoFiltered.getMenuItem().getBsMenu().getUrl())
                        		.icon(menudtoFiltered.getMenuItem().getBsMenu().getIcon())
                        		.build();
                        segundoSubmenu.getElements().add(item);
                    } else {
                        tercerSubmenu = DefaultSubMenu.builder().label(menudtoFiltered.getMenuItem().getTitulo()).build();
                        //segundoSubmenu.getElements().add(cargaSubItems(moduloActual.getCodModulo(), titulo, itemTitulos.getId(), tercerSubmenu));
                    }
                    subMenuModulo.getElements().add(segundoSubmenu);
			});
		});
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public BsUsuario getUsuarioLogueado() {
		
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(BsUsuario usuarioLogueado) {
		if (!Objects.isNull(usuarioLogueado)) {
			ListarMenu(usuarioLogueado);
		}
		this.usuarioLogueado = usuarioLogueado;
	}

	public LoginService getLoginServiceImpl() {
		return loginServiceImpl;
	}

	public void setLoginServiceImpl(LoginService loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}
	
	

}
