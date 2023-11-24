package py.com.capitalsys.capitalsysentities.entities.base;

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

/*
* 24 nov. 2023 - Elitebook
*/
@Entity
@Table(name = "bs_empresas")
public class BsEmpresa  extends Common {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nombre_fantasia")
	private String nombreFantasia;
    
	@Column(name = "direc_empresa")
    private String direcEmpresa;
	
	@ManyToOne
	@JoinColumn(name = "bs_personas_id", referencedColumnName = "id",nullable = false)
    private BsPersona bsPersona;

    public BsEmpresa() {
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

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public void setNombreFantasia(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
	}

	public String getDirecEmpresa() {
		return direcEmpresa;
	}

	public void setDirecEmpresa(String direcEmpresa) {
		this.direcEmpresa = direcEmpresa;
	}

	public BsPersona getBsPersona() {
		return bsPersona;
	}

	public void setBsPersona(BsPersona bsPersona) {
		this.bsPersona = bsPersona;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bsPersona, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BsEmpresa other = (BsEmpresa) obj;
		return Objects.equals(bsPersona, other.bsPersona) && Objects.equals(id, other.id);
	}
	
	

}
