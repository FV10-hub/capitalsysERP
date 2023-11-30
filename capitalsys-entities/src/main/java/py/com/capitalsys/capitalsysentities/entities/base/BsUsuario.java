package py.com.capitalsys.capitalsysentities.entities.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Aug 25, 2023
 * fvazquez
 * 
 */
@Entity
@Table(name = "bs_usuario")
public class BsUsuario extends Common {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cod_usuario")
    private String codUsuario;
	
	@Column(name = "password")
    private String password;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_bs_persona")
	private BsPersona bsPersona;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_bs_rol")
	private BsRol rol;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bs_empresa_id", referencedColumnName = "id", nullable = true)
    private BsEmpresa bsEmpresa;
	
	@PrePersist
	private void preInsert() {
		this.setFechaCreacion(LocalDateTime.now());
		this.setFechaActualizacion(LocalDateTime.now());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BsPersona getBsPersona() {
		return bsPersona;
	}

	public void setBsPersona(BsPersona bsPersona) {
		this.bsPersona = bsPersona;
	}

	public BsRol getRol() {
		return rol;
	}

	public void setRol(BsRol rol) {
		this.rol = rol;
	}
		
	public BsEmpresa getBsEmpresa() {
		return bsEmpresa;
	}

	public void setBsEmpresa(BsEmpresa bsEmpresa) {
		this.bsEmpresa = bsEmpresa;
	}

	public void encryptPassword() {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        this.password = passwordEncryptor.encryptPassword(this.password);
    }

    // Método para verificar la contraseña al hacer login
    public boolean checkPassword(String inputPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.checkPassword(inputPassword, this.password);
    }
    
    public String getPersonaNombreCompleto() {
        return bsPersona != null ? bsPersona.getNombreCompleto() : null;
    }
	
	
}
