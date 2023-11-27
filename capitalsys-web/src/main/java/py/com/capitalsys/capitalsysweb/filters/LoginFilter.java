package py.com.capitalsys.capitalsysweb.filters;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysweb.session.SessionBean;

/*
* 27 nov. 2023 - Elitebook
* Clase que implementa el filtro para verificar la sesion del usuario.
*/
//anotacion para configurar pero siempre debe ir implementado la interfaz filter
@WebFilter
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		HttpSession httpSession = httpServletRequest.getSession();

		// busco si existe una sesion activa
		if (httpSession == null || httpSession.getAttribute("sessionBean") == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
		} else {
			if (httpSession.getAttribute("sessionBean") != null) {
				SessionBean sessionBean = (SessionBean) httpSession.getAttribute("sessionBean");
				
				BsUsuario usuarioEnSesion = sessionBean.getUsuarioLogueado();
				//valido que exista un usuario logueado
				if (Objects.isNull(usuarioEnSesion)) {
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
					return;
				}
			}

			//si hay sesion de usuario dejo pasar
			chain.doFilter(httpServletRequest, httpServletResponse);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
