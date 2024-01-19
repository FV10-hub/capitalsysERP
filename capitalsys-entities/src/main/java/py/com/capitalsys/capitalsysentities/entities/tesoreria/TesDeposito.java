package py.com.capitalsys.capitalsysentities.entities.tesoreria;
/*
* 15 ene. 2024 - Elitebook
*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.Common;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrosValores;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;


@Entity
@Table(name = "tes_depositos", 
uniqueConstraints = 
@UniqueConstraint(name = 
"tes_depositos_uniques", columnNames = {
 "tes_banco_id", "bs_empresa_id","nro_boleta" }))
public class TesDeposito extends Common implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fecha_deposito")
	private LocalDate fechaDeposito;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "nro_boleta")
	private Long nroBoleta;
	
	@Column(name = "monto_total_deposito")
	private BigDecimal montoTotalDeposito;
	
	@ManyToOne
	@JoinColumn(name = "tes_banco_id", referencedColumnName = "id", nullable = false)
	private TesBanco tesBanco;
	
	@OneToOne
	@JoinColumn(name = "cob_habilitacion_id", referencedColumnName = "id", nullable = false)
	private CobHabilitacionCaja cobHabilitacionCaja;
	
	@ManyToOne
	@JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = false)
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

	public TesDeposito() {
		this.montoTotalDeposito = BigDecimal.ZERO;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaDeposito() {
		return fechaDeposito;
	}

	public void setFechaDeposito(LocalDate fechaDeposito) {
		this.fechaDeposito = fechaDeposito;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getNroBoleta() {
		return nroBoleta;
	}

	public void setNroBoleta(Long nroBoleta) {
		this.nroBoleta = nroBoleta;
	}

	public BigDecimal getMontoTotalDeposito() {
		return montoTotalDeposito;
	}

	public void setMontoTotalDeposito(BigDecimal montoTotalDeposito) {
		this.montoTotalDeposito = montoTotalDeposito;
	}

	public TesBanco getTesBanco() {
		return tesBanco;
	}

	public void setTesBanco(TesBanco tesBanco) {
		this.tesBanco = tesBanco;
	}

	public CobHabilitacionCaja getCobHabilitacionCaja() {
		return cobHabilitacionCaja;
	}

	public void setCobHabilitacionCaja(CobHabilitacionCaja cobHabilitacionCaja) {
		this.cobHabilitacionCaja = cobHabilitacionCaja;
	}

	public BsEmpresa getBsEmpresa() {
		return bsEmpresa;
	}

	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}
	
}

