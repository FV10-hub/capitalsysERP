package py.com.capitalsys.capitalsysweb.controllers.ventas.movimientos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsIva;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoComprobante;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCliente;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;
import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaCabecera;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaDetalle;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobClienteService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.stock.StoArticuloService;
import py.com.capitalsys.capitalsysservices.services.ventas.VenFacturasService;
import py.com.capitalsys.capitalsysservices.services.ventas.VenVendedorService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.CommonsUtilitiesController;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;
import py.com.capitalsys.capitalsysweb.utils.Modulos;

/*
* 4 ene. 2024 - Elitebook
*/
@ManagedBean
@ViewScoped
public class VenFacturasController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(VenFacturasController.class);

	private VenFacturaCabecera venFacturaCabecera, venFacturaCabeceraSelected;
	private CreDesembolsoCabecera creDesembolsoCabecera;
	private BsTalonario bsTalonarioSelected;
	private StoArticulo stoArticuloSelected;
	private VenFacturaDetalle detalle;
	private LazyDataModel<VenFacturaCabecera> lazyModel;
	private LazyDataModel<CreDesembolsoCabecera> lazyModelDesembolso;
	private LazyDataModel<StoArticulo> lazyModelArticulos;
	private LazyDataModel<BsTalonario> lazyModelTalonario;
	private LazyDataModel<CobCliente> lazyModelCliente;
	private LazyDataModel<VenVendedor> lazyModelVenVendedor;

	private List<String> estadoList;
	private boolean esNuegoRegistro;
	private boolean esVisibleFormulario = true;

	private static final String DT_NAME = "dt-facturas";
	private static final String DT_DIALOG_NAME = "manageFacturasDialog";

	// services
	@ManagedProperty("#{venFacturasServiceImpl}")
	private VenFacturasService venFacturasServiceImpl;

	@ManagedProperty("#{cobClienteServiceImpl}")
	private CobClienteService cobClienteServiceImpl;

	@ManagedProperty("#{venVendedorServiceImpl}")
	private VenVendedorService venVendedorServiceImpl;

	@ManagedProperty("#{creDesembolsoServiceImpl}")
	private CreDesembolsoService creDesembolsoServiceImpl;

	@ManagedProperty("#{stoArticuloServiceImpl}")
	private StoArticuloService stoArticuloServiceImpl;

	@ManagedProperty("#{bsParametroServiceImpl}")
	private BsParametroService bsParametroServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	@ManagedProperty("#{commonsUtilitiesController}")
	private CommonsUtilitiesController commonsUtilitiesController;

	@PostConstruct
	public void init() {
		this.cleanFields();

	}

	public void cleanFields() {
		this.venFacturaCabecera = null;
		this.venFacturaCabeceraSelected = null;
		this.creDesembolsoCabecera = null;
		this.bsTalonarioSelected = null;
		this.stoArticuloSelected = null;
		this.detalle = null;
		this.lazyModel = null;
		this.lazyModelDesembolso = null;
		this.lazyModelArticulos = null;
		this.lazyModelTalonario = null;
		this.lazyModelCliente = null;
		this.lazyModelVenVendedor = null;

		this.esNuegoRegistro = true;
		this.esVisibleFormulario = !esVisibleFormulario;
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado(), "ANULADO");
	}

	// GETTERS Y SETTERS
	public VenFacturaCabecera getVenFacturaCabecera() {
		if (Objects.isNull(venFacturaCabecera)) {
			venFacturaCabecera = new VenFacturaCabecera();
			venFacturaCabecera.setFechaFactura(LocalDate.now());
			venFacturaCabecera.setIdComprobate(null);
			venFacturaCabecera.setTipoFactura("FACTURA");
			venFacturaCabecera.setEstado(Estado.ACTIVO.getEstado());
			venFacturaCabecera.setBsEmpresa(new BsEmpresa());
			venFacturaCabecera.setBsTalonario(new BsTalonario());
			venFacturaCabecera.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			venFacturaCabecera.setCobCliente(new CobCliente());
			venFacturaCabecera.getCobCliente().setBsPersona(new BsPersona());
			venFacturaCabecera.setVenVendedor(new VenVendedor());
			venFacturaCabecera.getVenVendedor().setBsPersona(new BsPersona());
			venFacturaCabecera.setCreDesembolsoCabecera(new CreDesembolsoCabecera());
			venFacturaCabecera.getCreDesembolsoCabecera().setCreSolicitudCredito(new CreSolicitudCredito());
			venFacturaCabecera.getCreDesembolsoCabecera().getCreSolicitudCredito().setCobCliente(new CobCliente());
			venFacturaCabecera.getCreDesembolsoCabecera().getCreSolicitudCredito().getCobCliente()
					.setBsPersona(new BsPersona());
			venFacturaCabecera.getCreDesembolsoCabecera().getCreSolicitudCredito().setVenVendedor(new VenVendedor());
			venFacturaCabecera.getCreDesembolsoCabecera().getCreSolicitudCredito().getVenVendedor()
					.setBsPersona(new BsPersona());

		}
		return venFacturaCabecera;
	}

	public void setVenFacturaCabecera(VenFacturaCabecera venFacturaCabecera) {
		this.venFacturaCabecera = venFacturaCabecera;
	}

	public VenFacturaCabecera getVenFacturaCabeceraSelected() {
		if (Objects.isNull(venFacturaCabeceraSelected)) {
			venFacturaCabeceraSelected = new VenFacturaCabecera();
			venFacturaCabeceraSelected.setFechaFactura(LocalDate.now());
			venFacturaCabeceraSelected.setBsEmpresa(new BsEmpresa());
			venFacturaCabeceraSelected.setBsTalonario(new BsTalonario());
			venFacturaCabeceraSelected.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			venFacturaCabeceraSelected.setCobCliente(new CobCliente());
			venFacturaCabeceraSelected.getCobCliente().setBsPersona(new BsPersona());
			venFacturaCabeceraSelected.setVenVendedor(new VenVendedor());
			venFacturaCabeceraSelected.getVenVendedor().setBsPersona(new BsPersona());
			venFacturaCabeceraSelected.setCreDesembolsoCabecera(new CreDesembolsoCabecera());
			venFacturaCabeceraSelected.getCreDesembolsoCabecera().setCreSolicitudCredito(new CreSolicitudCredito());
			venFacturaCabeceraSelected.getCreDesembolsoCabecera().getCreSolicitudCredito()
					.setCobCliente(new CobCliente());
			venFacturaCabeceraSelected.getCreDesembolsoCabecera().getCreSolicitudCredito().getCobCliente()
					.setBsPersona(new BsPersona());
			venFacturaCabeceraSelected.getCreDesembolsoCabecera().getCreSolicitudCredito()
					.setVenVendedor(new VenVendedor());
			venFacturaCabeceraSelected.getCreDesembolsoCabecera().getCreSolicitudCredito().getVenVendedor()
					.setBsPersona(new BsPersona());

		}
		return venFacturaCabeceraSelected;
	}

	public void setVenFacturaCabeceraSelected(VenFacturaCabecera venFacturaCabeceraSelected) {
		if (!Objects.isNull(venFacturaCabeceraSelected)) {
			venFacturaCabecera = venFacturaCabeceraSelected;
			venFacturaCabeceraSelected = null;
			this.esNuegoRegistro = false;
			this.esVisibleFormulario = true;
		}
		this.venFacturaCabeceraSelected = venFacturaCabeceraSelected;
	}

	public CreDesembolsoCabecera getCreDesembolsoCabecera() {
		if (Objects.isNull(creDesembolsoCabecera)) {
			creDesembolsoCabecera = new CreDesembolsoCabecera();
			creDesembolsoCabecera.setFechaDesembolso(LocalDate.now());
			creDesembolsoCabecera.setEstado(Estado.ACTIVO.getEstado());
			creDesembolsoCabecera.setTazaAnual(BigDecimal.ZERO);
			creDesembolsoCabecera.setTazaMora(BigDecimal.ZERO);
			creDesembolsoCabecera.setBsEmpresa(new BsEmpresa());
			creDesembolsoCabecera.setBsTalonario(new BsTalonario());
			creDesembolsoCabecera.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			creDesembolsoCabecera.setCreTipoAmortizacion(new CreTipoAmortizacion());
			creDesembolsoCabecera.setCreSolicitudCredito(new CreSolicitudCredito());
			creDesembolsoCabecera.getCreSolicitudCredito().setCobCliente(new CobCliente());
			creDesembolsoCabecera.getCreSolicitudCredito().getCobCliente().setBsPersona(new BsPersona());
			creDesembolsoCabecera.getCreSolicitudCredito().setVenVendedor(new VenVendedor());
			creDesembolsoCabecera.getCreSolicitudCredito().getVenVendedor().setBsPersona(new BsPersona());
		}
		return creDesembolsoCabecera;
	}

	public void setCreDesembolsoCabecera(CreDesembolsoCabecera creDesembolsoCabecera) {
		if (!Objects.isNull(creDesembolsoCabecera)) {
			this.venFacturaCabecera.setCreDesembolsoCabecera(creDesembolsoCabecera);
			this.venFacturaCabecera.setVenVendedor(creDesembolsoCabecera.getCreSolicitudCredito().getVenVendedor());
			this.venFacturaCabecera.setCobCliente(creDesembolsoCabecera.getCreSolicitudCredito().getCobCliente());
			this.venFacturaCabecera.setIdComprobate(creDesembolsoCabecera.getId());
			this.venFacturaCabecera.setTipoFactura("DESEMBOLSO");
			cargarDetalleSiEsDesembolso();
			creDesembolsoCabecera = null;
		}
		this.creDesembolsoCabecera = creDesembolsoCabecera;
	}

	public BsTalonario getBsTalonarioSelected() {
		if (Objects.isNull(bsTalonarioSelected)) {
			this.bsTalonarioSelected = new BsTalonario();
			this.bsTalonarioSelected.setEstado(Estado.ACTIVO.getEstado());
			this.bsTalonarioSelected.setBsTimbrado(new BsTimbrado());
			this.bsTalonarioSelected.setBsTipoComprobante(new BsTipoComprobante());
		}
		return bsTalonarioSelected;
	}

	public void setBsTalonarioSelected(BsTalonario bsTalonarioSelected) {
		if (!Objects.isNull(bsTalonarioSelected)) {
			this.venFacturaCabecera.setBsTalonario(bsTalonarioSelected);
			bsTalonarioSelected = null;
		}
		this.bsTalonarioSelected = bsTalonarioSelected;
	}

	public StoArticulo getStoArticuloSelected() {
		if (Objects.isNull(stoArticuloSelected)) {
			this.stoArticuloSelected = new StoArticulo();
			this.stoArticuloSelected.setBsEmpresa(new BsEmpresa());
			this.stoArticuloSelected.setBsIva(new BsIva());
		}
		return stoArticuloSelected;
	}

	public void setStoArticuloSelected(StoArticulo stoArticuloSelected) {
		if (stoArticuloSelected != null) {
			detalle.setStoArticulo(stoArticuloSelected);
			detalle.setCantidad(1);
			detalle.setCodIva(stoArticuloSelected.getBsIva().getCodIva());
			detalle.setPrecioUnitario(stoArticuloSelected.getPrecioUnitario());
			detalle.setMontoLinea(stoArticuloSelected.getPrecioUnitario());
		}
		this.stoArticuloSelected = stoArticuloSelected;
	}

	public VenFacturaDetalle getDetalle() {
		if (Objects.isNull(detalle)) {
			detalle = new VenFacturaDetalle();
			detalle.setVenFacturaCabecera(new VenFacturaCabecera());
			detalle.setStoArticulo(new StoArticulo());
		}
		return detalle;
	}

	public void setDetalle(VenFacturaDetalle detalle) {
		this.detalle = detalle;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public boolean isEsNuegoRegistro() {
		return esNuegoRegistro;
	}

	public void setEsNuegoRegistro(boolean esNuegoRegistro) {
		this.esNuegoRegistro = esNuegoRegistro;
	}

	public boolean isEsVisibleFormulario() {
		return esVisibleFormulario;
	}

	public void setEsVisibleFormulario(boolean esVisibleFormulario) {
		this.esVisibleFormulario = esVisibleFormulario;
	}
	
	

	// LAZY
	public LazyDataModel<VenFacturaCabecera> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<VenFacturaCabecera>(
					(List<VenFacturaCabecera>) venFacturasServiceImpl
							.buscarVenFacturaCabeceraActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<VenFacturaCabecera> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<CreDesembolsoCabecera> getLazyModelDesembolso() {
		if (Objects.isNull(lazyModelDesembolso)) {
			lazyModelDesembolso = new GenericLazyDataModel<CreDesembolsoCabecera>(
					(List<CreDesembolsoCabecera>) creDesembolsoServiceImpl.buscarCreDesembolsoCabeceraActivosLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelDesembolso;
	}

	public void setLazyModelDesembolso(LazyDataModel<CreDesembolsoCabecera> lazyModelDesembolso) {
		this.lazyModelDesembolso = lazyModelDesembolso;
	}

	public LazyDataModel<StoArticulo> getLazyModelArticulos() {
		if (Objects.isNull(lazyModelArticulos)) {
			lazyModelArticulos = new GenericLazyDataModel<StoArticulo>((List<StoArticulo>) stoArticuloServiceImpl
					.buscarStoArticuloActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModelArticulos;
	}

	public void setLazyModelArticulos(LazyDataModel<StoArticulo> lazyModelArticulos) {
		this.lazyModelArticulos = lazyModelArticulos;
	}

	public LazyDataModel<BsTalonario> getLazyModelTalonario() {
		if (Objects.isNull(lazyModelTalonario)) {
			var moduloCredito = this.bsModuloServiceImpl.findByCodigo(Modulos.VENTAS.getModulo());
			lazyModelTalonario = new GenericLazyDataModel<BsTalonario>(
					this.commonsUtilitiesController.bsTalonarioPorModuloLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada(), moduloCredito.getId()));

		}
		return lazyModelTalonario;
	}

	public void setLazyModelTalonario(LazyDataModel<BsTalonario> lazyModelTalonario) {
		this.lazyModelTalonario = lazyModelTalonario;
	}

	public LazyDataModel<CobCliente> getLazyModelCliente() {
		if (Objects.isNull(lazyModelCliente)) {
			lazyModelCliente = new GenericLazyDataModel<CobCliente>((List<CobCliente>) cobClienteServiceImpl
					.buscarClienteActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModelCliente;
	}

	public void setLazyModelCliente(LazyDataModel<CobCliente> lazyModelCliente) {
		this.lazyModelCliente = lazyModelCliente;
	}

	public LazyDataModel<VenVendedor> getLazyModelVenVendedor() {
		if (Objects.isNull(lazyModelVenVendedor)) {
			lazyModelVenVendedor = new GenericLazyDataModel<VenVendedor>((List<VenVendedor>) venVendedorServiceImpl
					.buscarVenVendedorActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModelVenVendedor;
	}

	public void setLazyModelVenVendedor(LazyDataModel<VenVendedor> lazyModelVenVendedor) {
		this.lazyModelVenVendedor = lazyModelVenVendedor;
	}

	// SERVICES
	public VenFacturasService getVenFacturasServiceImpl() {
		return venFacturasServiceImpl;
	}

	public void setVenFacturasServiceImpl(VenFacturasService venFacturasServiceImpl) {
		this.venFacturasServiceImpl = venFacturasServiceImpl;
	}

	public CobClienteService getCobClienteServiceImpl() {
		return cobClienteServiceImpl;
	}

	public void setCobClienteServiceImpl(CobClienteService cobClienteServiceImpl) {
		this.cobClienteServiceImpl = cobClienteServiceImpl;
	}

	public VenVendedorService getVenVendedorServiceImpl() {
		return venVendedorServiceImpl;
	}

	public void setVenVendedorServiceImpl(VenVendedorService venVendedorServiceImpl) {
		this.venVendedorServiceImpl = venVendedorServiceImpl;
	}

	public CreDesembolsoService getCreDesembolsoServiceImpl() {
		return creDesembolsoServiceImpl;
	}

	public void setCreDesembolsoServiceImpl(CreDesembolsoService creDesembolsoServiceImpl) {
		this.creDesembolsoServiceImpl = creDesembolsoServiceImpl;
	}

	public StoArticuloService getStoArticuloServiceImpl() {
		return stoArticuloServiceImpl;
	}

	public void setStoArticuloServiceImpl(StoArticuloService stoArticuloServiceImpl) {
		this.stoArticuloServiceImpl = stoArticuloServiceImpl;
	}

	public BsParametroService getBsParametroServiceImpl() {
		return bsParametroServiceImpl;
	}

	public void setBsParametroServiceImpl(BsParametroService bsParametroServiceImpl) {
		this.bsParametroServiceImpl = bsParametroServiceImpl;
	}

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public CommonsUtilitiesController getCommonsUtilitiesController() {
		return commonsUtilitiesController;
	}

	public void setCommonsUtilitiesController(CommonsUtilitiesController commonsUtilitiesController) {
		this.commonsUtilitiesController = commonsUtilitiesController;
	}

	// METODOS
	public void calculaTotalLineaDetalle() {
		detalle.setMontoLinea(detalle.getStoArticulo().getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())));
	}
	
	public void abrirDialogoAddDetalle() {
		detalle = new VenFacturaDetalle();
		detalle.setVenFacturaCabecera(new VenFacturaCabecera());
		detalle.setStoArticulo(new StoArticulo());
	}
	
	public void eliminaDetalle() {
		venFacturaCabecera.getVenFacturaDetalleList().removeIf(det -> det.equals(detalle));
		this.detalle = null;
		this.venFacturaCabecera.calcularTotales();
		PrimeFaces.current().ajax().update(":form:dt-detalle");
	}

	public void agregarDetalle() {
		if (!(Objects.isNull(venFacturaCabecera.getCreDesembolsoCabecera())
				|| Objects.isNull(venFacturaCabecera.getCreDesembolsoCabecera().getId()))) {
			cargarDetalleSiEsDesembolso();
			return;
		}
		cargaDetalleVenta();
	}

	private void cargarDetalleSiEsDesembolso() {
		limpiarDetalle();
		try {
			this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("DES",
					this.commonsUtilitiesController.getIdEmpresaLogueada());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al generar cuotas", e);
			e.printStackTrace(System.err);
		}
		this.detalle = new VenFacturaDetalle();
		this.detalle.setStoArticulo(stoArticuloSelected);
		this.detalle.setCantidad(1);
		this.detalle.setNroOrden(1);
		this.detalle.setCodIva(stoArticuloSelected.getBsIva().getCodIva());
		//TODO: revisar aca si esta bien que sete el total del interes nomas
		this.detalle.setMontoLinea(venFacturaCabecera.getCreDesembolsoCabecera().getMontoTotalInteres().add(venFacturaCabecera.getCreDesembolsoCabecera().getMontoTotalIva()));

		this.venFacturaCabecera.addDetalle(detalle);
		this.venFacturaCabecera.calcularTotales();
		this.venFacturaCabecera.setCabeceraADetalle();

		detalle = null;
		PrimeFaces.current().ajax().update(":form:dt-detalle");
	}

	private void cargaDetalleVenta() {
		if (Objects.isNull(detalle.getCodIva())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					"Debe seleccionar un Articulo para definir el tipo impuesto.");
			return;
		}
		if (CollectionUtils.isEmpty(venFacturaCabecera.getVenFacturaDetalleList())) {
			detalle.setNroOrden(1);
		} else {
			Optional<Integer> maxNroOrden = venFacturaCabecera.getVenFacturaDetalleList().stream()
					.map(VenFacturaDetalle::getNroOrden)
					.max(Integer::compareTo);
			if (maxNroOrden.isPresent()) {
				detalle.setNroOrden(maxNroOrden.get() + 1);
			} else {
				detalle.setNroOrden(1);
			}
		}
		venFacturaCabecera.setMontoTotalGravada(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalExenta(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalIva(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalFactura(BigDecimal.ZERO);

		this.venFacturaCabecera.addDetalle(detalle);
		this.venFacturaCabecera.calcularTotales();
		this.venFacturaCabecera.setCabeceraADetalle();

		detalle = null;
		PrimeFaces.current().ajax().update(":form:dt-detalle");
	}

	public void limpiarDetalle() {
		venFacturaCabecera.setVenFacturaDetalleList(new ArrayList<VenFacturaDetalle>());
		venFacturaCabecera.setMontoTotalGravada(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalExenta(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalIva(BigDecimal.ZERO);
		venFacturaCabecera.setMontoTotalFactura(BigDecimal.ZERO);
		PrimeFaces.current().ajax().update(":form:btnLimpiar");
	}

	public void guardar() {
		try {
			if (Objects.isNull(this.venFacturaCabecera.getBsTalonario())
					|| Objects.isNull(this.venFacturaCabecera.getBsTalonario().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"Debe seleccionar un Talonario.");
				return;
			}
			if (CollectionUtils.isEmpty(venFacturaCabecera.getVenFacturaDetalleList())
					|| venFacturaCabecera.getVenFacturaDetalleList().size() == 0) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"Debe cargar algun detalle para guardar.");
				return;
			}
			this.venFacturaCabecera.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			this.venFacturaCabecera.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
			if (Objects.isNull(this.venFacturaCabecera.getId())) {
				this.venFacturaCabecera.setIndImpreso("N");
				this.venFacturaCabecera.setNroFactura(this.venFacturasServiceImpl
						.calcularNroFacturaDisponible(commonsUtilitiesController.getIdEmpresaLogueada(),
								this.venFacturaCabecera.getBsTalonario().getId()));
				String formato = "%s-%s-%09d";
				this.venFacturaCabecera.setNroFacturaCompleto(String.format(formato,
						this.venFacturaCabecera.getBsTalonario().getBsTimbrado().getCodEstablecimiento(),
						this.venFacturaCabecera.getBsTalonario().getBsTimbrado().getCodExpedicion(),
						this.venFacturaCabecera.getNroFactura()));
			}
			if (!Objects.isNull(this.venFacturasServiceImpl.save(this.venFacturaCabecera))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			e.printStackTrace(System.err);

			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause instanceof ConstraintViolationException) {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
							"La factura ya fue creada.");
					break;
				}
				cause = cause.getCause();
			}

			if (cause == null) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						e.getMessage().substring(0, e.getMessage().length()) + "...");
			}

			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		}

	}

	public void delete() {
		try {
			if (Objects.isNull("S".equalsIgnoreCase(this.venFacturaCabecera.getIndImpreso()))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Ya fue impreso.");
				return;
			}
			if (!Objects.isNull(this.venFacturaCabecera)) {
				this.venFacturasServiceImpl.deleteById(this.venFacturaCabecera.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			// e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getMessage().substring(0, e.getMessage().length()) + "...");
		}

	}

}
