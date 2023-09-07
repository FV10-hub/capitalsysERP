/**
 * 
 */
package py.com.capitalsys.capitalsysweb.session;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import py.com.capitalsys.capitalsysentities.dto.MenuDto;
import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.LoginService;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;

/**
 * @author DevPredator Clase que mantendra la informacion en la sesion del
 *         usuario.
 */
//@ManagedBean
//@SessionScoped
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

	@PostConstruct
	public void init() {

		this.ListarMenu();
		this.construirMenu();

	}

	private void ListarMenu() {
		try {
			this.menuListFromDB = this.loginServiceImpl.consultarMenuPorUsuario(this.usuarioLogueado.getId());
			this.moduloList = this.bsModuloServiceImpl.buscarTodosLista();
		} catch (Exception e) {
			// TODO: handle exception
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
			.filter(menmod -> menmod.getMenu().getBsModulo().getId() == modulo.getId())
			.forEach(menudtoFiltered -> {
				if (menudtoFiltered.getMenu().getTipoMenu().equalsIgnoreCase("SUBMENU")) {
					DefaultSubMenu primerSubMenu = DefaultSubMenu.builder().label(modulo.getNombre()).icon("pi pi-fw pi-cog")
							.build();
					BsMenu subMenu = menudtoFiltered.getMenu().getSubMenuPadre();
					if (!Objects.isNull(subMenu)) {
						if (subMenu.getLabel().equalsIgnoreCase(menudtoFiltered.getMenu().getLabel())) {
							DefaultMenuItem itemHijo = DefaultMenuItem.builder()
									.value(menudtoFiltered.getMenu().getNombre()).build();
							primerSubMenu.getElements().add(itemHijo);
						}
						
					}
					this.model.getElements().add(primerSubMenu);
				}else {
					DefaultMenuItem itemHuerfano = DefaultMenuItem.builder()
							.value(menudtoFiltered.getMenu().getNombre()).build();
					model.getElements().add(itemHuerfano);
				}
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
		if (!Objects.isNull(this.usuarioLogueado)) {
			construirMenu();
		}
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(BsUsuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

}
