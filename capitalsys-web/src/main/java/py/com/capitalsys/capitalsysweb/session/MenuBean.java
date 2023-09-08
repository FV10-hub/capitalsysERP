/**
 * 
 */
package py.com.capitalsys.capitalsysweb.session;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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
import py.com.capitalsys.capitalsysservices.services.base.BsMenuItemService;
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
	private List<MenuDto> menuListFromDB;
	private BsUsuario usuarioLogueado;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{loginServiceImpl}")
	private LoginService loginServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	@ManagedProperty("#{bsMenuItemServiceImpl}")
	private BsMenuItemService bsMenuItemServiceImpl;

	private MenuModel model;
	private DefaultSubMenu subMenuModulo;
	// TODO: esto debe tener el inicio del ID para los estilos css
	private static String MENU_ID_BASE = "m_";
	// TODO: esto debe tener el inicio del ID para los estilos css
	private static String SUB_MENU_ID_BASE = "m_sm";

	private AtomicInteger idSubMenuIncrementable = new AtomicInteger(0);
	private AtomicInteger idSubMenuItemIncrementable = new AtomicInteger(0);
	private AtomicInteger idSubMenuItemHijoIncrementable = new AtomicInteger(0);

	private void ListarMenu(BsUsuario user) {
		try {
			this.menuListFromDB = this.loginServiceImpl.consultarMenuPorUsuario(user.getId());
			this.moduloList = this.bsModuloServiceImpl.buscarModulosActivosLista();
			if (CollectionUtils.isNotEmpty(menuListFromDB) && CollectionUtils.isNotEmpty(moduloList)) {
				this.construirMenu();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void construirMenu() {
		this.model = new DefaultMenuModel();
		this.moduloList.stream().forEach(modulosActivo -> {
			if (poseePermisoSobreModuloActual(modulosActivo)) {
				// TODO: MENU
				this.subMenuModulo = DefaultSubMenu.builder()
						.expanded(true)
						.id(MENU_ID_BASE + modulosActivo.getCodigo())
						.label(modulosActivo.getNombre()).icon(modulosActivo.getIcon()).build();
				List<BsMenuItem> listaAgrupadores = this.bsMenuItemServiceImpl.findMenuAgrupado(modulosActivo.getId());
				if (CollectionUtils.isNotEmpty(listaAgrupadores)) {
					listaAgrupadores.stream().forEach(agrupador -> {
						// TODO: DEFINICIONES, REPORTES, MOVIMIENTOS
						var idSubMenuForHTML = SUB_MENU_ID_BASE + idSubMenuIncrementable.get();
						DefaultSubMenu subMenuAgrupador = DefaultSubMenu.builder().id(idSubMenuForHTML)
								.label(agrupador.getTitulo()).icon(agrupador.getIcon()).build();

						List<BsMenuItem> listaItemDelAgrupador = this.bsMenuItemServiceImpl
								.findMenuItemAgrupado(agrupador.getId());
						if (CollectionUtils.isNotEmpty(listaItemDelAgrupador)) {
							listaItemDelAgrupador.stream().forEach((itemDelAgrupador) -> {
								System.out.println("ANTES DE PERMISO:::: " + itemDelAgrupador.getBsMenu().getNombre());
								if (poseePermisoSobreMenuActual(itemDelAgrupador.getBsMenu())) {
									System.out.println(
											"DESPUES DE PERMISO:::: " + itemDelAgrupador.getBsMenu().getNombre());
									// TODO: aca pregunto si es menu o item
									if ("SUBMENU".equalsIgnoreCase(itemDelAgrupador.getBsMenu().getTipoMenu())) {
										var idSubMenuEsPadre = idSubMenuForHTML+idSubMenuItemIncrementable.get();
										DefaultSubMenu subMenu = DefaultSubMenu.builder()
												.id(idSubMenuEsPadre)
												.label(agrupador.getTitulo()).icon(agrupador.getIcon()).build();
										System.out.println("SUBMENU TIPO :::: " + agrupador.getTitulo());
										List<BsMenuItem> listaHijosDelSubMenu = this.bsMenuItemServiceImpl
												.findMenuItemAgrupado(itemDelAgrupador.getId());
										if (CollectionUtils.isNotEmpty(listaItemDelAgrupador)) {
											listaHijosDelSubMenu.stream().forEach(hijosDelSubMenu -> {
												if (poseePermisoSobreMenuActual(hijosDelSubMenu.getBsMenu())) {
													System.out.println("TUVO HIJO :::: " + hijosDelSubMenu.getTitulo());
													DefaultMenuItem itemHijo = DefaultMenuItem.builder()
															.id(idSubMenuEsPadre+idSubMenuItemHijoIncrementable.get())
															.value(itemDelAgrupador.getTitulo())
															.icon(itemDelAgrupador.getIcon())
															.outcome(itemDelAgrupador.getBsMenu().getUrl())
															// .command("#{menuView.update}")
															// .update("messages")
															.build();
													subMenu.getElements().add(itemHijo);
												}

											});
										}
										// TODO: aca puedo validar que si no tiene hijos no construya el menuagrupador
										subMenuAgrupador.getElements().add(subMenu);
									} else {
										System.out.println("HUERFANO :::: " + itemDelAgrupador.getTitulo());
										DefaultMenuItem item = DefaultMenuItem.builder()
												.id(idSubMenuForHTML+idSubMenuItemIncrementable.get()) 
												.value(itemDelAgrupador.getTitulo()).icon(itemDelAgrupador.getIcon())
												.outcome(itemDelAgrupador.getBsMenu().getUrl())
												// .command("#{menuView.update}")
												// .update("messages")
												.build();
										subMenuAgrupador.getElements().add(item);
									}
								}
							});
						}
						// TODO: ACA LE AGREGO EL GRUPO AL MODULO
						subMenuModulo.getElements().add(subMenuAgrupador);
					});
				}

			}
		});
		this.model.getElements().add(this.subMenuModulo);
	}

	private boolean poseePermisoSobreModuloActual(BsModulo modulo) {
		return this.menuListFromDB.stream()
				.anyMatch(menuItem -> menuItem.getMenuItem().getBsMenu().getId() == modulo.getId());
	}

	private boolean poseePermisoSobreMenuActual(BsMenu menu) {
		return this.menuListFromDB.stream()
				.anyMatch(menuItem -> menuItem.getMenuItem().getBsMenu().getId() == menu.getId());
	}

	private DefaultMenuItem crearMenuItem(BsMenu menu) {
		return DefaultMenuItem.builder().value(menu.getNombre()).icon("pi pi-save").outcome(menu.getUrl()).ajax(false)
				// .command("#{menuView.save}")
				// .update("messages")
				.build();
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

	public BsMenuItemService getBsMenuItemServiceImpl() {
		return bsMenuItemServiceImpl;
	}

	public void setBsMenuItemServiceImpl(BsMenuItemService bsMenuItemServiceImpl) {
		this.bsMenuItemServiceImpl = bsMenuItemServiceImpl;
	}

}
