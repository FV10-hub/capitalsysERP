package py.com.capitalsys.capitalsysweb.controllers.base;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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

	/*
	 * @ManagedProperty("#{menuBean}") private MenuBean menuBean;
	 */
	// metodos
	public void login() {
		BsUsuario usuarioConsultado = this.loginServiceImpl.consultarUsuarioLogin(this.username, this.password);
		if (usuarioConsultado != null) {
			try {
				this.sessionBean.setUsuarioLogueado(usuarioConsultado);
				//this.menuBean.setUsuarioLogueado(usuarioConsultado);

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

}
