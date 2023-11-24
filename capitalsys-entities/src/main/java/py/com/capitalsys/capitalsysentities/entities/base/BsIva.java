package py.com.capitalsys.capitalsysentities.entities.base;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
* Aug 30, 2023-5:26:37 PM-fvazquez
**/
@Entity
@Table(name = "bs_iva")
public class BsIva extends Common {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name = "cod_iva")
    private String codIva;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
	
	public BsIva() {
		super();
	}


	@PrePersist
	private void preInsert() {
		this.setEstado("ACTIVO");
		this.setFechaCreacion(LocalDateTime.now());
		this.setFechaActualizacion(LocalDateTime.now());
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getCodIva() {
		return codIva;
	}



	public void setCodIva(String codIva) {
		this.codIva = codIva;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public BigDecimal getPorcentaje() {
		return porcentaje;
	}



	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BsIva other = (BsIva) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	

}
