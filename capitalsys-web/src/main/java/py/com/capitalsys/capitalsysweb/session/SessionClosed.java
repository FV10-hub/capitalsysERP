/**
 * 
 */
package py.com.capitalsys.capitalsysweb.session;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import py.com.capitalsys.capitalsysweb.utils.CommonUtils;

/**
 * @author DevPredator Clase que permite cerrar la sesion del usuario y
 *         redireccionar a la pantalla del login.
 */
@ManagedBean
@ViewScoped
public class SessionClosed {

	/**
	 * Metodo que permite cerrar la sesion del usuario.
	 */
	public void cerrarSesion() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			externalContext = ((ExternalContext) externalContext.getSession(false));
			externalContext.invalidateSession();
			
			// Controlar la caché del navegador
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			CommonUtils.redireccionar("/login.xhtml");
		} catch (IOException e) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡Ups!",
					"Hubo un problema al tratar de regresar al login, favor de intentar más tarde.");
			e.printStackTrace();
		}
	}
}
