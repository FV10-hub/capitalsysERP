/**
 * 
 */
package py.com.capitalsys.capitalsysweb.session;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

/**
 * @author DevPredator
 * Clase que mantendra la informacion en la sesion del usuario.
 */
@ManagedBean
@SessionScoped
public class MenuBean {
	/**
	 * Objeto persona que se mantendra en la sesion.
	 */
	private BsUsuario usuarioLogueado;
	
	@PostConstruct
	public void init() {
		System.out.println("Creando sesion...");
	}

	public BsUsuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(BsUsuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	
}
