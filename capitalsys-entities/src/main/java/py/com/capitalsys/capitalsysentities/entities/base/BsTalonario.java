package py.com.capitalsys.capitalsysentities.entities.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/*
* 2 ene. 2024 - Elitebook
*/
@Entity
@Table(name = "bs_talonarios")
public class BsTalonario extends Common implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "bs_timbrado_id", referencedColumnName = "id", nullable = false)
	private BsTimbrado bsTimbrado;
	
	@ManyToOne
	@JoinColumn(name = "bs_tipo_comprobante_id", referencedColumnName = "id", nullable = false)
	private BsTipoComprobante bsTipoComprobante;
	
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

	public BsTimbrado getBsTimbrado() {
		return bsTimbrado;
	}

	public void setBsTimbrado(BsTimbrado bsTimbrado) {
		this.bsTimbrado = bsTimbrado;
	}

	public BsTipoComprobante getBsTipoComprobante() {
		return bsTipoComprobante;
	}

	public void setBsTipoComprobante(BsTipoComprobante bsTipoComprobante) {
		this.bsTipoComprobante = bsTipoComprobante;
	}
	
	
	
}



