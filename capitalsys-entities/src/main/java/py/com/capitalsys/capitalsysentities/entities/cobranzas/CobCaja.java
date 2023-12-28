package py.com.capitalsys.capitalsysentities.entities.cobranzas;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsUsuario;
import py.com.capitalsys.capitalsysentities.entities.base.Common;

/*
* 28 dic. 2023 - Elitebook
*/
@Entity
@Table(name = "cob_cajas")
public class CobCaja extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cod_caja")
    private String codCaja;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bs_usuario_id")
	private BsUsuario bsUsuario;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = true)
	private BsEmpresa bsEmpresa;
	
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

	public String getCodCaja() {
		return codCaja;
	}

	public void setCodCaja(String codCaja) {
		this.codCaja = codCaja;
	}

	public BsUsuario getBsUsuario() {
		return bsUsuario;
	}

	public void setBsUsuario(BsUsuario bsUsuario) {
		this.bsUsuario = bsUsuario;
	}

	public BsEmpresa getBsEmpresa() {
		return bsEmpresa;
	}

	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}
	

}

