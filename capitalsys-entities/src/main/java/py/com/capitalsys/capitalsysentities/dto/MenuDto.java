package py.com.capitalsys.capitalsysentities.dto;
/**
* Sep 4, 2023-3:19:25 PM-fvazquez
* esta clase me va servir para armar el menu segun los permisos del usuario
**/

import py.com.capitalsys.capitalsysentities.entities.base.BsMenu;
import py.com.capitalsys.capitalsysentities.entities.base.BsPermisoRol;
import py.com.capitalsys.capitalsysentities.entities.base.BsRol;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public class MenuDto {
	
	private BsUsuario usuario;
	private BsRol rol;
	private BsPermisoRol permiso;
	private BsMenu menu;
	public MenuDto() {
		super();
	}
	public MenuDto(BsUsuario usuario, BsRol rol, BsPermisoRol permiso, BsMenu menu) {
		super();
		this.usuario = usuario;
		this.rol = rol;
		this.permiso = permiso;
		this.menu = menu;
	}
	public BsUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(BsUsuario usuario) {
		this.usuario = usuario;
	}
	public BsRol getRol() {
		return rol;
	}
	public void setRol(BsRol rol) {
		this.rol = rol;
	}
	public BsPermisoRol getPermiso() {
		return permiso;
	}
	public void setPermiso(BsPermisoRol permiso) {
		this.permiso = permiso;
	}
	public BsMenu getMenu() {
		return menu;
	}
	public void setMenu(BsMenu menu) {
		this.menu = menu;
	}

	
}
