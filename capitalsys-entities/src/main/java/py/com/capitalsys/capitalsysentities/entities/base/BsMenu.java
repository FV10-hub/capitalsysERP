package py.com.capitalsys.capitalsysentities.entities.base;

import java.io.Serializable;
import java.util.Set;

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
import javax.persistence.Table;

/**
* Aug 30, 2023-5:28:44 PM-fvazquez
**/
@Entity
@Table(name = "bs_menu")
public class BsMenu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "label")
    private String label;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "url")
    private String url;
	
	@Column(name = "tipo_menu")
    private String tipoMenu;
	
	@Column(name = "tipo_menu_agrupador")
    private String tipoMenuAgrupador;
	
	@Column(name = "nro_orden")
    private String orden;
	
	@ManyToOne
    @JoinColumn(name = "id_sub_menu", nullable = true)
    private BsMenu subMenuPadre;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bs_modulo")
	private BsModulo bsModulo;
	
	@OneToMany(mappedBy = "bsMenu", cascade = CascadeType.REMOVE)
	private Set<BsMenuItem> bsMenuItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BsModulo getBsModulo() {
		return bsModulo;
	}

	public void setBsModulo(BsModulo bsModulo) {
		this.bsModulo = bsModulo;
	}

	public String getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(String tipoMenu) {
		this.tipoMenu = tipoMenu;
	}

	public BsMenu getSubMenuPadre() {
		return subMenuPadre;
	}

	public void setSubMenuPadre(BsMenu subMenuPadre) {
		this.subMenuPadre = subMenuPadre;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Set<BsMenuItem> getBsMenuItem() {
		return bsMenuItem;
	}

	public void setBsMenuItem(Set<BsMenuItem> bsMenuItem) {
		this.bsMenuItem = bsMenuItem;
	}

	public String getTipoMenuAgrupador() {
		return tipoMenuAgrupador;
	}

	public void setTipoMenuAgrupador(String tipoMenuAgrupador) {
		this.tipoMenuAgrupador = tipoMenuAgrupador;
	}

	
}
