package py.com.capitalsys.capitalsysweb.controllers.base;

import java.io.IOException;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysservices.services.LoginService;
import py.com.capitalsys.capitalsysweb.session.MenuBean;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;

/**
 * Este controlador se va encargar de manejar el flujo de inicio de sesion
 **/
@ManagedBean
@ViewScoped
public class LoginController {
	
	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	// atributos
	private String username;
	private String password;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{loginServiceImpl}")
	private LoginService loginServiceImpl;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	
	  @ManagedProperty("#{menuBean}") 
	  private MenuBean menuBean;
	 
	// metodos
	public void login() {
		BsUsuario usuarioConsultado = this.loginServiceImpl.consultarUsuarioLogin(this.username, this.password);
		if (!Objects.isNull(usuarioConsultado)) {
			try {
				this.sessionBean.setUsuarioLogueado(usuarioConsultado);
				this.menuBean.setUsuarioLogueado(usuarioConsultado);
				LOGGER.warn("WARN");
				LOGGER.info("INFO");
				LOGGER.error("error");
				CommonUtils.redireccionar("/pages/commons/dashboard.xhtml");
			} catch (IOException e) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_FATAL, "¡ERROR!",
						"Formato incorrecto en cual se ingresa a la pantalla deseada.");
			}
		} else {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡UPS!",
					"El usuario y/o contraseña son incorrectos");
		}

	}
	
	public void loginEncrypt() {
		BsUsuario usuarioConsultado = this.loginServiceImpl.findByUsuario(this.username);
		boolean passDesencriptado = usuarioConsultado.checkPassword(this.password);
		if (passDesencriptado) {
			try {
				this.sessionBean.setUsuarioLogueado(usuarioConsultado);
				this.menuBean.setUsuarioLogueado(usuarioConsultado);
				LOGGER.warn("WARN");
				LOGGER.info("INFO");
				LOGGER.error("error");
				CommonUtils.redireccionar("/pages/commons/dashboard.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_FATAL, "¡ERROR!",
						"Formato incorrecto en cual se ingresa a la pantalla deseada.");
			}
		} else {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡UPS!",
					"El usuario y/o contraseña son incorrectos");
		}

	}

	// getters y setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginServiceImpl
	 */
	public LoginService getLoginServiceImpl() {
		return loginServiceImpl;
	}

	/**
	 * @param loginServiceImpl the loginServiceImpl to set
	 */
	public void setLoginServiceImpl(LoginService loginServiceImpl) {
		this.loginServiceImpl = loginServiceImpl;
	}

	/**
	 * @return the sessionBean
	 */
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	/**
	 * @param sessionBean the sessionBean to set
	 */
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}
	
	

}
