package py.com.capitalsys.capitalsysentities.entities.compras;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.Common;

/*
* 1 dic. 2023 - Elitebook
*/
@Entity
@Table(name = "com_proveedores", uniqueConstraints = @UniqueConstraint(name= "com_proveedores_unique_persona_empresa" ,
columnNames = {"bs_empresa_id", "bs_persona_id"}))
public class ComProveedor extends Common implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "cod_proveedor")
	private String codProveedor;

	@OneToOne()
	@JoinColumn(name = "bs_persona_id")
	private BsPersona bsPersona;

	@ManyToOne
	@JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = false)
	private BsEmpresa bsEmpresa;

	@PrePersist
	private void preInsert() {
		this.setEstado("ACTIVO");
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

	public String getCodProveedor() {
		return codProveedor;
	}

	public void setCodProveedor(String codProveedor) {
		this.codProveedor = codProveedor;
	}

	public BsPersona getBsPersona() {
		return bsPersona;
	}

	public void setBsPersona(BsPersona bsPersona) {
		this.bsPersona = bsPersona;
	}

	public BsEmpresa getBsEmpresa() {
		return bsEmpresa;
	}

	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bsEmpresa, bsPersona);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComProveedor other = (ComProveedor) obj;
		return Objects.equals(bsEmpresa, other.bsEmpresa) && Objects.equals(bsPersona, other.bsPersona);
	}

	
	

}
