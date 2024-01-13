package py.com.capitalsys.capitalsysweb.controllers.ventas.movimientos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Column;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobSaldo;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoDetalle;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;
import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenCondicionVenta;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaCabecera;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenFacturaDetalle;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobClienteService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobSaldoService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.stock.StoArticuloService;
import py.com.capitalsys.capitalsysservices.services.ventas.VenCondicionVentaService;
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
	private LazyDataModel<VenCondicionVenta> lazyModelVenCondicionVenta;
	List<CobSaldo> listaSaldoAGenerar;

	private List<String> estadoList;
	private boolean esNuegoRegistro;
	private boolean esVisibleFormulario = true;

	private static final String DT_NAME = "dt-facturas";

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

	@ManagedProperty("#{venCondicionVentaServiceImpl}")
	private VenCondicionVentaService venCondicionVentaServiceImpl;

	@ManagedProperty("#{cobSaldoServiceImpl}")
	private CobSaldoService cobSaldoServiceImpl;

	// CobSaldoServiceImpl

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
		this.lazyModelVenCondicionVenta = null;

		this.esNuegoRegistro = true;
		this.esVisibleFormulario = !esVisibleFormulario;
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado(), "ANULADO");
		this.listaSaldoAGenerar = new ArrayList<CobSaldo>();
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
			venFacturaCabecera.setVenCondicionVenta(new VenCondicionVenta());
			venFacturaCabecera.setVenCondicionVenta(new VenCondicionVenta());
			venFacturaCabecera.setBsTalonario(new BsTalonario());
			venFacturaCabecera.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			venFacturaCabecera.setCobCliente(new CobCliente());
			venFacturaCabecera.getCobCliente().setBsPersona(new BsPersona());
			venFacturaCabecera.setVenVendedor(new VenVendedor());
			venFacturaCabecera.getVenVendedor().setBsPersona(new BsPersona());

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
			venFacturaCabeceraSelected.setVenCondicionVenta(new VenCondicionVenta());
			venFacturaCabeceraSelected.setVenCondicionVenta(new VenCondicionVenta());
			venFacturaCabeceraSelected.setBsTalonario(new BsTalonario());
			venFacturaCabeceraSelected.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			venFacturaCabeceraSelected.setCobCliente(new CobCliente());
			venFacturaCabeceraSelected.getCobCliente().setBsPersona(new BsPersona());
			venFacturaCabeceraSelected.setVenVendedor(new VenVendedor());
			venFacturaCabeceraSelected.getVenVendedor().setBsPersona(new BsPersona());

		}
		return venFacturaCabeceraSelected;
	}

	public void setVenFacturaCabeceraSelected(VenFacturaCabecera venFacturaCabeceraSelected) {
		if (!Objects.isNull(venFacturaCabeceraSelected)) {
			venFacturaCabeceraSelected.getVenFacturaDetalleList()
					.sort(Comparator.comparing(VenFacturaDetalle::getNroOrden));
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
			var moduloCredito = this.bsModuloServiceImpl.findByCodigo(Modulos.VENTAS.getModulo());
			var condicionVentaValorParametrizado = this.commonsUtilitiesController.getValorParametro("CREDES",
					moduloCredito.getId());
			Optional<VenCondicionVenta> condicion = this.venCondicionVentaServiceImpl
					.buscarVenCondicionVentaActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada())
					.stream().filter(con -> con.getCodCondicion().equalsIgnoreCase(condicionVentaValorParametrizado))
					.findFirst();
			if (condicion.isPresent()) {
				this.venFacturaCabecera.setVenCondicionVenta(condicion.get());
			}
			this.venFacturaCabecera.setIdComprobate(creDesembolsoCabecera.getId());
			this.venFacturaCabecera.setVenVendedor(creDesembolsoCabecera.getCreSolicitudCredito().getVenVendedor());
			this.venFacturaCabecera.setCobCliente(creDesembolsoCabecera.getCreSolicitudCredito().getCobCliente());
			this.venFacturaCabecera.setIdComprobate(creDesembolsoCabecera.getId());
			this.venFacturaCabecera.setTipoFactura("DESEMBOLSO");
			cargarDetalleSiEsDesembolso(creDesembolsoCabecera);
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
			BigDecimal existencia = this.stoArticuloServiceImpl.retornaExistenciaArticulo(stoArticuloSelected.getId(),
					this.commonsUtilitiesController.getIdEmpresaLogueada());
			if (stoArticuloSelected.getIndInventariable().equalsIgnoreCase("S")
					&& existencia.compareTo(BigDecimal.ZERO) <= 0) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"El Articulo no tiene existencia suficiente.");
				PrimeFaces.current().ajax().update("form:messages", "form:btnAddDetalle");
				return;
			}
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
			lazyModel = new GenericLazyDataModel<VenFacturaCabecera>((List<VenFacturaCabecera>) venFacturasServiceImpl
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
					(List<CreDesembolsoCabecera>) creDesembolsoServiceImpl.buscarCreDesembolsoAFacturarLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelDesembolso;
	}

	public void setLazyModelDesembolso(LazyDataModel<CreDesembolsoCabecera> lazyModelDesembolso) {
		this.lazyModelDesembolso = lazyModelDesembolso;
	}

	public LazyDataModel<StoArticulo> getLazyModelArticulos() {
		if (Objects.isNull(lazyModelArticulos)) {
			List<StoArticulo> listaFiltrada = (List<StoArticulo>) stoArticuloServiceImpl
					.buscarStoArticuloActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()).stream()
					.filter(articulo -> "S".equals(articulo.getIndInventariable())).collect(Collectors.toList());
			lazyModelArticulos = new GenericLazyDataModel<StoArticulo>(listaFiltrada);
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
					.buscarClienteActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelCliente;
	}

	public void setLazyModelCliente(LazyDataModel<CobCliente> lazyModelCliente) {
		this.lazyModelCliente = lazyModelCliente;
	}

	public LazyDataModel<VenVendedor> getLazyModelVenVendedor() {
		if (Objects.isNull(lazyModelVenVendedor)) {
			lazyModelVenVendedor = new GenericLazyDataModel<VenVendedor>((List<VenVendedor>) venVendedorServiceImpl
					.buscarVenVendedorActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelVenVendedor;
	}

	public void setLazyModelVenVendedor(LazyDataModel<VenVendedor> lazyModelVenVendedor) {
		this.lazyModelVenVendedor = lazyModelVenVendedor;
	}

	public LazyDataModel<VenCondicionVenta> getLazyModelVenCondicionVenta() {
		if (Objects.isNull(lazyModelVenCondicionVenta)) {
			lazyModelVenCondicionVenta = new GenericLazyDataModel<VenCondicionVenta>(
					(List<VenCondicionVenta>) venCondicionVentaServiceImpl.buscarVenCondicionVentaActivosLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelVenCondicionVenta;
	}

	public void setLazyModelVenCondicionVenta(LazyDataModel<VenCondicionVenta> lazyModelVenCondicionVenta) {
		this.lazyModelVenCondicionVenta = lazyModelVenCondicionVenta;
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

	public VenCondicionVentaService getVenCondicionVentaServiceImpl() {
		return venCondicionVentaServiceImpl;
	}

	public void setVenCondicionVentaServiceImpl(VenCondicionVentaService venCondicionVentaServiceImpl) {
		this.venCondicionVentaServiceImpl = venCondicionVentaServiceImpl;
	}
	
	public CobSaldoService getCobSaldoServiceImpl() {
		return cobSaldoServiceImpl;
	}

	public void setCobSaldoServiceImpl(CobSaldoService cobSaldoServiceImpl) {
		this.cobSaldoServiceImpl = cobSaldoServiceImpl;
	}

	// METODOS
	public void calculaTotalLineaDetalle() {
		BigDecimal existencia = this.stoArticuloServiceImpl.retornaExistenciaArticulo(detalle.getStoArticulo().getId(),
				this.commonsUtilitiesController.getIdEmpresaLogueada());
		if (detalle.getStoArticulo().getIndInventariable().equalsIgnoreCase("S")
				&& existencia.compareTo(new BigDecimal(detalle.getCantidad())) < 0) {
			detalle.setStoArticulo(new StoArticulo());
			detalle.setCantidad(1);
			detalle.setCodIva(null);
			detalle.setPrecioUnitario(BigDecimal.ZERO);
			detalle.setMontoLinea(BigDecimal.ZERO);
			stoArticuloSelected = null;
			getStoArticuloSelected();
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					"El Articulo no tiene existencia suficiente.");
			PrimeFaces.current().ajax().update("form:messages", "form:articuloLb", "form:codIvaLb", "form:totalineaLb",
					"form:precioLb", "form:form:btnAddDetalle");
			return;
		}
		detalle.setMontoLinea(
				detalle.getStoArticulo().getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())));
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
		if (StringUtils.equalsIgnoreCase("DESEMBOLSO", venFacturaCabecera.getTipoFactura())) {
			cargarDetalleSiEsDesembolso(creDesembolsoCabecera);
			return;
		}
		if (StringUtils.equalsIgnoreCase("NCR", venFacturaCabecera.getTipoFactura())) {
			// TODO: aca debo implementar notas de creditos
			return;
		}
		cargaDetalleVenta();
	}

	private void cargarDetalleSiEsDesembolso(CreDesembolsoCabecera creDesembolsoCabecera) {
		limpiarDetalle();
		try {
			this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("DES",
					this.commonsUtilitiesController.getIdEmpresaLogueada());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al generar cuotas", e);
			e.printStackTrace(System.err);
		}
		this.detalle = new VenFacturaDetalle();
		this.detalle.setEstado(Estado.ACTIVO.getEstado());
		this.detalle.setUsuarioModificacion(this.sessionBean.getUsuarioLogueado().getCodUsuario());
		this.detalle.setStoArticulo(stoArticuloSelected);
		this.detalle.setCantidad(1);
		this.detalle.setNroOrden(1);
		this.detalle.setCodIva(stoArticuloSelected.getBsIva().getCodIva());
		// TODO: revisar aca si esta bien que sete el total del interes nomas
		this.detalle.setMontoLinea(
				creDesembolsoCabecera.getMontoTotalInteres().add(creDesembolsoCabecera.getMontoTotalIva()));
		this.detalle.setPrecioUnitario(this.detalle.getMontoLinea());

		this.venFacturaCabecera.addDetalle(detalle);
		this.venFacturaCabecera.calcularTotales();
		this.venFacturaCabecera.setCabeceraADetalle();
		prepararSaldoAGenerarCredito(creDesembolsoCabecera);

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
					.map(VenFacturaDetalle::getNroOrden).max(Integer::compareTo);
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

		this.detalle.setEstado(Estado.ACTIVO.getEstado());
		this.detalle.setUsuarioModificacion(this.sessionBean.getUsuarioLogueado().getCodUsuario());
		this.venFacturaCabecera.addDetalle(detalle);
		this.venFacturaCabecera.calcularTotales();
		this.venFacturaCabecera.setCabeceraADetalle();

		detalle = null;
		PrimeFaces.current().ajax().update(":form:dt-detalle", ":form:btnGuardar");
	}

	private void prepararSaldoAGenerarCredito(CreDesembolsoCabecera desembolso) {
		if (!Objects.isNull(desembolso)) {
			desembolso.getCreDesembolsoDetalleList().forEach(detalle -> {
				CobSaldo saldo = new CobSaldo();

				saldo.setIdComprobate(desembolso.getId());
				saldo.setTipoComprobante("DESEMBOLSO");
				saldo.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
				saldo.setNroComprobanteCompleto(desembolso.getNroDesembolso().toString());

				// montos
				saldo.setIdCuota(detalle.getId());
				saldo.setNroCuota(detalle.getNroCuota().intValue());
				saldo.setMontoCuota(detalle.getMontoCuota());
				saldo.setSaldoCuota(detalle.getMontoCuota());

				// otros
				saldo.setFechaVencimiento(detalle.getFechaVencimiento());
				saldo.setCobCliente(this.venFacturaCabecera.getCobCliente());
				saldo.setBsEmpresa(desembolso.getBsEmpresa());
				this.listaSaldoAGenerar.add(saldo);
			});
		}
	}

	private void parseaDetalleASaldo(VenFacturaCabecera factura) {
		if (!Objects.isNull(factura)) {

			boolean tipoCondicion = "CON".equalsIgnoreCase(factura.getVenCondicionVenta().getCodCondicion());
			this.listaSaldoAGenerar = new ArrayList<CobSaldo>();
			if (tipoCondicion) {
				CobSaldo saldo = new CobSaldo();

				saldo.setIdComprobate(factura.getId());
				saldo.setTipoComprobante("FACTURA");
				saldo.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
				saldo.setNroComprobanteCompleto(factura.getNroFacturaCompleto());
				
				// montos
				saldo.setIdCuota(factura.getId());
				saldo.setNroCuota(1);
				saldo.setMontoCuota(factura.getMontoTotalFactura());
				saldo.setSaldoCuota(factura.getMontoTotalFactura());
				
				saldo.setFechaVencimiento(factura.getFechaFactura());
				
				saldo.setCobCliente(this.venFacturaCabecera.getCobCliente());
				saldo.setBsEmpresa(factura.getBsEmpresa());
				this.listaSaldoAGenerar.add(saldo);
				
			} else {
				int plazo = factura.getVenCondicionVenta().getPlazo().intValue();
				BigDecimal montoCuota = factura.getMontoTotalFactura()
						.divide(factura.getVenCondicionVenta().getPlazo());
				LocalDate fechaVencimiento = LocalDate.of(factura.getFechaFactura().getYear(),
						factura.getFechaFactura().getMonth(), factura.getFechaFactura().getDayOfMonth());
				int diaHabilPrimerVencimiento = fechaVencimiento.getDayOfMonth();
				for (int i = 1; i <= plazo; i++) {
					CobSaldo saldo = new CobSaldo();

					saldo.setIdComprobate(factura.getId());
					saldo.setTipoComprobante("FACTURA");
					saldo.setNroComprobanteCompleto(factura.getNroFacturaCompleto());

					// montos
					saldo.setIdCuota(factura.getId());
					saldo.setNroCuota(i);
					saldo.setMontoCuota(montoCuota);
					saldo.setSaldoCuota(montoCuota);
					
					saldo.setFechaVencimiento(fechaVencimiento);

					// otros
					if (i == 1) {
						saldo.setFechaVencimiento(fechaVencimiento);
						fechaVencimiento = fechaVencimiento.plusMonths(1);
					} else {
						int ultimoDiaHabilActual = YearMonth.from(fechaVencimiento).atEndOfMonth().getDayOfMonth();
						if (ultimoDiaHabilActual < diaHabilPrimerVencimiento) {
							fechaVencimiento = LocalDate.of(fechaVencimiento.getYear(), fechaVencimiento.getMonth(),
									ultimoDiaHabilActual);
							saldo.setFechaVencimiento(fechaVencimiento);
							fechaVencimiento = LocalDate.of(fechaVencimiento.getYear(),
									fechaVencimiento.plusMonths(1).getMonth(), diaHabilPrimerVencimiento);
						} else if (fechaVencimiento.getMonth() == Month.FEBRUARY) {
							fechaVencimiento = LocalDate.of(fechaVencimiento.getYear(), fechaVencimiento.getMonth(),
									diaHabilPrimerVencimiento);
							saldo.setFechaVencimiento(fechaVencimiento);
							fechaVencimiento = LocalDate.of(fechaVencimiento.getYear(),
									fechaVencimiento.plusMonths(1).getMonth(), diaHabilPrimerVencimiento);
						} else {
							saldo.setFechaVencimiento(fechaVencimiento);
							fechaVencimiento = fechaVencimiento.plusMonths(1);
						}
					}
					saldo.setCobCliente(this.venFacturaCabecera.getCobCliente());
					saldo.setBsEmpresa(factura.getBsEmpresa());
					this.listaSaldoAGenerar.add(saldo);
				}
			}

		}
	}

	public void limpiarDetalle() {
		this.listaSaldoAGenerar = new ArrayList<CobSaldo>();
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
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar un Talonario.");
				return;
			}
			if (Objects.isNull(this.venFacturaCabecera.getVenCondicionVenta())
					|| Objects.isNull(this.venFacturaCabecera.getVenCondicionVenta().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"Debe seleccionar una Condicion de Venta.");
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
				this.venFacturaCabecera.setIndImpresoBoolean(false);
				this.venFacturaCabecera.setNroFactura(this.venFacturasServiceImpl.calcularNroFacturaDisponible(
						commonsUtilitiesController.getIdEmpresaLogueada(),
						this.venFacturaCabecera.getBsTalonario().getId()));
				String formato = "%s-%s-%09d";
				this.venFacturaCabecera.setNroFacturaCompleto(String.format(formato,
						this.venFacturaCabecera.getBsTalonario().getBsTimbrado().getCodEstablecimiento(),
						this.venFacturaCabecera.getBsTalonario().getBsTimbrado().getCodExpedicion(),
						this.venFacturaCabecera.getNroFactura()));
			}
			VenFacturaCabecera facturaGuardada = this.venFacturasServiceImpl.save(this.venFacturaCabecera);
			if (!Objects.isNull(facturaGuardada)) {
				//TODO: aca debo ver el caso de NCR
				if(facturaGuardada.getTipoFactura().equalsIgnoreCase("FACTURA")) {
					parseaDetalleASaldo(facturaGuardada);
				}
				if (!Objects.isNull(this.cobSaldoServiceImpl.saveAll(listaSaldoAGenerar))) {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
							"El registro se guardo correctamente.");	
				}else {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
							"No se Pudieron crear los saldos.");
				}
					
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_FATAL, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			e.printStackTrace(System.err);

			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause instanceof ConstraintViolationException) {
					CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "La factura ya fue creada.");
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
