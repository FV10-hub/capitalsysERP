package py.com.capitalsys.capitalsysentities.entities.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
* Aug 30, 2023-5:26:37 PM-fvazquez
**/
@Entity
@Table(name = "bs_modulo", uniqueConstraints = @UniqueConstraint(name= "bs_modulo_unique_codigo" ,columnNames = {"codigo"}))
public class BsModulo extends Common {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "codigo")
    private String codigo;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "icon")
    private String icon;
	
	@Column(name = "nro_orden")
    private int nroOrden;
	
	@Column(name = "path")
    private String path;
	
	
	@PrePersist
	private void preInsert() {
		this.setFechaCreacion(LocalDateTime.now());
		this.setFechaActualizacion(LocalDateTime.now());
	}
	
	@PreUpdate
	private void preUpdate() {
		this.setFechaActualizacion(LocalDateTime.now());
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}


	public int getNroOrden() {
		return nroOrden;
	}


	public void setNroOrden(int nroOrden) {
		this.nroOrden = nroOrden;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	

}
