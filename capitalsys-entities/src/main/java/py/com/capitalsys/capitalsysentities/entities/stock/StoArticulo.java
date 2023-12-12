package py.com.capitalsys.capitalsysentities.entities.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsIva;
import py.com.capitalsys.capitalsysentities.entities.base.Common;

/*
* 12 dic. 2023 - Elitebook
*/
@Entity
@Table(name = "sto_articulos")
public class StoArticulo extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cod_articulo")
    private String codArticulo;
	
	@Column(name = "descripcion")
    private String descripcion;
	
	@Column(name = "precio_unitario")
    private BigDecimal precioUnio;
	
	@Column(name = "ind_inventariable")
    private String indInventariable;
	
	@Transient
	private boolean indInventariableAux;
	
	@ManyToOne
	@JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = false)
	private BsEmpresa bsEmpresa;
	
	@ManyToOne
	@JoinColumn(name = "bs_iva_id", referencedColumnName = "id", nullable = false)
	private BsIva bsIva;
	
	@PrePersist
	private void preInsert() {
		//this.setEstado("ACTIVO");
		this.setFechaCreacion(LocalDateTime.now());
		this.setFechaActualizacion(LocalDateTime.now());
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCodArticulo() {
		return codArticulo;
	}


	public void setCodArticulo(String codArticulo) {
		this.codArticulo = codArticulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public BigDecimal getPrecioUnio() {
		return precioUnio;
	}


	public void setPrecioUnio(BigDecimal precioUnio) {
		this.precioUnio = precioUnio;
	}


	public String getIndInventariable() {
		return indInventariable;
	}


	public void setIndInventariable(String indInventariable) {
		this.indInventariable = indInventariable;
	}


	public boolean isIndInventariableAux() {
		return indInventariableAux;
	}


	public void setIndInventariableAux(boolean indInventariableAux) {
		this.indInventariableAux = indInventariableAux;
	}


	public BsEmpresa getBsEmpresa() {
		return bsEmpresa;
	}


	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}


	public BsIva getBsIva() {
		return bsIva;
	}


	public void setBsIva(BsIva bsIva) {
		this.bsIva = bsIva;
	}


	@Override
	public int hashCode() {
		return Objects.hash(bsEmpresa, bsIva, codArticulo, id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoArticulo other = (StoArticulo) obj;
		return Objects.equals(bsEmpresa, other.bsEmpresa) && Objects.equals(bsIva, other.bsIva)
				&& Objects.equals(codArticulo, other.codArticulo) && Objects.equals(id, other.id);
	}
	
	

}

