package py.com.capitalsys.capitalsysentities.entities.base;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
* Sep 7, 2023-3:30:46 PM-fvazquez
**/
@Entity
@Table(name = "bs_menu_item")
public class BsMenuItem extends Common implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
    
    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "nro_orden")
    private String orden;
        
    @Column(name = "id_menu_item")
    private Long idMenuItem;
    
    @JoinColumn(name = "id_bs_menu", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BsMenu bsMenu;
    
    @JoinColumn(name = "id_bs_modulo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BsModulo bsModulo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Long getIdMenuItem() {
		return idMenuItem;
	}

	public void setIdMenuItem(Long idMenuItem) {
		this.idMenuItem = idMenuItem;
	}

	public BsMenu getBsMenu() {
		return bsMenu;
	}

	public void setBsMenu(BsMenu bsMenu) {
		this.bsMenu = bsMenu;
	}

	public BsModulo getBsModulo() {
		return bsModulo;
	}

	public void setBsModulo(BsModulo bsModulo) {
		this.bsModulo = bsModulo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BsMenuItem other = (BsMenuItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(titulo, other.titulo);
	}
    
    
    
}
