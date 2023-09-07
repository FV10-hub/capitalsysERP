package py.com.capitalsys.capitalsysentities.dto;
/**
* Sep 4, 2023-3:19:25 PM-fvazquez
* esta clase me va servir para armar el menu segun los permisos del usuario
**/

import py.com.capitalsys.capitalsysentities.entities.base.BsMenuItem;
import py.com.capitalsys.capitalsysentities.entities.base.BsPermisoRol;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;

public class MenuDto {
	
	private BsMenuItem menuItem;
	private BsPermisoRol permiso;
	private BsUsuario usuario;
	public MenuDto(BsMenuItem menuItem, BsPermisoRol permiso, BsUsuario usuario) {
		super();
		this.menuItem = menuItem;
		this.permiso = permiso;
		this.usuario = usuario;
	}
	public BsMenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(BsMenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public BsPermisoRol getPermiso() {
		return permiso;
	}
	public void setPermiso(BsPermisoRol permiso) {
		this.permiso = permiso;
	}
	public BsUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(BsUsuario usuario) {
		this.usuario = usuario;
	}


	
}
