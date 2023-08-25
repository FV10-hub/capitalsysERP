package py.com.capitalsys.capitalsysentities.entities.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToOne
	@JoinColumn(name = "id_bs_persona")
	private BsPersona bsPersona;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idRol")
	private BsRol rol;
		
	public BsRol getRol() {
		return rol;
	}

	public void setRol(BsRol rol) {
		this.rol = rol;
	}
	
	
}
