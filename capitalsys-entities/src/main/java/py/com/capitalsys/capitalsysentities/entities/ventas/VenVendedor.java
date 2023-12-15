package py.com.capitalsys.capitalsysentities.entities.ventas;

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

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.Common;

/*
* 12 dic. 2023 - Elitebook
*/
@Entity
@Table(name = "ven_vendedores")
public class VenVendedor extends Common implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "cod_vendedor")
	private String codVendedor;

	@OneToOne()
	@JoinColumn(name = "id_bs_persona")
	private BsPersona bsPersona;

	@ManyToOne
	@JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = false)
	private BsEmpresa bsEmpresa;

	@PrePersist
	private void preInsert() {
		// this.setEstado("ACTIVO");
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

	public String getCodVendedor() {
		return codVendedor;
	}

	public void setCodVendedor(String codVendedor) {
		this.codVendedor = codVendedor;
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
		return Objects.hash(bsEmpresa, bsPersona, codVendedor, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VenVendedor other = (VenVendedor) obj;
		return Objects.equals(bsEmpresa, other.bsEmpresa) && Objects.equals(bsPersona, other.bsPersona)
				&& Objects.equals(codVendedor, other.codVendedor) && Objects.equals(id, other.id);
	}

}
