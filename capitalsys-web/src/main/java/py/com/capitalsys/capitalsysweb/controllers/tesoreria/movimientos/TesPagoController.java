package py.com.capitalsys.capitalsysweb.controllers.tesoreria.movimientos;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.entities.base.BsEmpresa;
import py.com.capitalsys.capitalsysentities.entities.base.BsPersona;
import py.com.capitalsys.capitalsysentities.entities.base.BsTalonario;
import py.com.capitalsys.capitalsysentities.entities.base.BsTimbrado;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoComprobante;
import py.com.capitalsys.capitalsysentities.entities.base.BsTipoValor;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCliente;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobCobrosValores;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobHabilitacionCaja;
import py.com.capitalsys.capitalsysentities.entities.compras.ComProveedor;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoCabecera;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesBanco;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoCabecera;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoComprobanteDetalle;
import py.com.capitalsys.capitalsysentities.entities.tesoreria.TesPagoValores;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsTipoValorService;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobClienteService;
import py.com.capitalsys.capitalsysservices.services.compras.ComProveedorService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.tesoreria.TesPagoService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.CommonsUtilitiesController;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;
import py.com.capitalsys.capitalsysweb.utils.Modulos;

/*
* 22 ene. 2024 - Elitebook
*/
@ManagedBean
@ViewScoped
public class TesPagoController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(TesPagoController.class);

	// PROPIOS
	private TesPagoCabecera tesPagoCabecera, tesPagoCabeceraSelected;
	private TesPagoComprobanteDetalle tesPagoComprobanteDetalleSelected;
	private List<TesPagoComprobanteDetalle> tesPagoComprobanteDetallesList;
	private TesPagoValores tesPagoValoresSelected;
	private List<TesPagoValores> tesPagoValoresList;
	private List<CreDesembolsoCabecera> desembolsoList;

	// FKs
	private BsTalonario bsTalonarioSelected;
	private CobCliente cobClienteSelected;
	private ComProveedor comProveedorSelected;

	// LAZY
	private LazyDataModel<TesPagoCabecera> lazyModel;
	private LazyDataModel<CreDesembolsoCabecera> lazyModelDesembolso;
	private LazyDataModel<BsTalonario> lazyModelTalonario;
	private LazyDataModel<CobCliente> lazyModelCliente;
	private LazyDataModel<ComProveedor> lazyModelProveedor;
	private LazyDataModel<BsTipoValor> lazyModelTipoValor;

	private List<String> estadoList;
	private boolean esNuegoRegistro;
	private boolean esVisibleFormulario = true;
	private boolean estaAutorizado;
	public BigDecimal montoTotalPago = BigDecimal.ZERO;
	private String tipoSaldoAFiltrar;

	private static final String DT_NAME = "dt-pagos";

	// services
	@ManagedProperty("#{tesPagoServiceImpl}")
	private TesPagoService tesPagoServiceImpl;

	@ManagedProperty("#{creDesembolsoServiceImpl}")
	private CreDesembolsoService creDesembolsoServiceImpl;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;

	@ManagedProperty("#{cobClienteServiceImpl}")
	private CobClienteService cobClienteServiceImpl;

	@ManagedProperty("#{bsTipoValorServiceImpl}")
	private BsTipoValorService bsTipoValorServiceImpl;

	@ManagedProperty("#{comProveedorServiceImpl}")
	private ComProveedorService comProveedorServiceImpl;

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
		// objetos
		this.tesPagoCabecera = null;
		this.tesPagoCabeceraSelected = null;
		this.tesPagoComprobanteDetalleSelected = null;
		this.tesPagoValoresSelected = null;
		this.bsTalonarioSelected = null;
		this.cobClienteSelected = null;
		this.comProveedorSelected = null;
		this.desembolsoList = null;

		// lazy
		this.lazyModel = null;
		this.lazyModelDesembolso = null;
		this.lazyModelTalonario = null;
		this.lazyModelCliente = null;
		this.lazyModelProveedor = null;
		this.lazyModelTipoValor = null;

		this.esNuegoRegistro = true;
		this.estaAutorizado = false;
		this.esVisibleFormulario = !esVisibleFormulario;
		this.tipoSaldoAFiltrar = "";

		// listas
		tesPagoComprobanteDetallesList = new ArrayList<>();
		tesPagoValoresList = new ArrayList<>();
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado(), "ANULADO");

	}

	// GETTERS Y SETTERS
	public TesPagoCabecera getTesPagoCabecera() {
		if (Objects.isNull(tesPagoCabecera)) {
			tesPagoCabecera = new TesPagoCabecera();
			tesPagoCabecera.setFechaPago(LocalDate.now());
			tesPagoCabecera.setEstado(Estado.ACTIVO.getEstado());
			tesPagoCabecera.setIndAutorizado("N");
			tesPagoCabecera.setBsEmpresa(new BsEmpresa());
			tesPagoCabecera.setCobHabilitacionCaja(new CobHabilitacionCaja());
			tesPagoCabecera.setBsTalonario(new BsTalonario());
			tesPagoCabecera.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			if (!this.commonsUtilitiesController.validarSiTengaHabilitacionAbierta()) {
				this.tesPagoCabecera.setCobHabilitacionCaja(this.commonsUtilitiesController.getHabilitacionAbierta());
			} else {
				validarCajaDelUsuario(true);
			}

		}
		return tesPagoCabecera;
	}

	public void setTesPagoCabecera(TesPagoCabecera tesPagoCabecera) {
		this.tesPagoCabecera = tesPagoCabecera;
	}

	public TesPagoCabecera getTesPagoCabeceraSelected() {
		if (Objects.isNull(tesPagoCabeceraSelected)) {
			tesPagoCabeceraSelected = new TesPagoCabecera();
			tesPagoCabeceraSelected.setFechaPago(LocalDate.now());
			tesPagoCabeceraSelected.setEstado(Estado.ACTIVO.getEstado());
			tesPagoCabeceraSelected.setIndAutorizado("N");
			tesPagoCabeceraSelected.setBsEmpresa(new BsEmpresa());
			tesPagoCabeceraSelected.setCobHabilitacionCaja(new CobHabilitacionCaja());
			tesPagoCabeceraSelected.setBsTalonario(new BsTalonario());
			tesPagoCabeceraSelected.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
			if (!this.commonsUtilitiesController.validarSiTengaHabilitacionAbierta()) {
				this.tesPagoCabecera.setCobHabilitacionCaja(this.commonsUtilitiesController.getHabilitacionAbierta());
			} else {
				validarCajaDelUsuario(true);
			}

		}
		return tesPagoCabeceraSelected;
	}

	public void setTesPagoCabeceraSelected(TesPagoCabecera tesPagoCabeceraSelected) {
		if (!Objects.isNull(tesPagoCabeceraSelected)) {
			tesPagoCabeceraSelected.getTesPagoComprobanteDetallesList()
					.sort(Comparator.comparing(TesPagoComprobanteDetalle::getNroOrden));
			tesPagoCabeceraSelected.getTesPagoValoresList().sort(Comparator.comparing(TesPagoValores::getNroOrden));
			this.estaAutorizado = tesPagoCabeceraSelected.getIndAutorizado().equalsIgnoreCase("S");
			if (this.estaAutorizado) {
				/*
				 * this.cobrosValoresList =
				 * this.cobCobrosValoresServiceImpl.buscarValoresPorComprobanteLista(
				 * this.commonsUtilitiesController.getIdEmpresaLogueada(),
				 * cobReciboCabeceraSelected.getId(), "RECIBO");
				 */
				this.montoTotalPago = BigDecimal.ZERO;/*
														 * cobrosValoresList.stream().map(CobCobrosValores::
														 * getMontoValor) .reduce(BigDecimal.ZERO, BigDecimal::add);
														 */
				// getResultadoResta();
			}
			tesPagoCabecera = tesPagoCabeceraSelected;
			tesPagoCabeceraSelected = null;
			this.esNuegoRegistro = false;
			this.esVisibleFormulario = true;
		}
		this.tesPagoCabeceraSelected = tesPagoCabeceraSelected;
	}

	public TesPagoComprobanteDetalle getTesPagoComprobanteDetalleSelected() {
		if (Objects.isNull(tesPagoComprobanteDetalleSelected)) {
			tesPagoComprobanteDetalleSelected = new TesPagoComprobanteDetalle();
			tesPagoComprobanteDetalleSelected.setMontoPagado(BigDecimal.ZERO);
			tesPagoComprobanteDetalleSelected.setTesPagoCabecera(new TesPagoCabecera());
		}
		return tesPagoComprobanteDetalleSelected;
	}

	public void setTesPagoComprobanteDetalleSelected(TesPagoComprobanteDetalle tesPagoComprobanteDetalleSelected) {
		this.tesPagoComprobanteDetalleSelected = tesPagoComprobanteDetalleSelected;
	}

	public TesPagoValores getTesPagoValoresSelected() {
		if (Objects.isNull(tesPagoValoresSelected)) {
			tesPagoValoresSelected = new TesPagoValores();
			tesPagoValoresSelected.setFechaValor(LocalDate.now());
			tesPagoValoresSelected.setFechaVencimiento(LocalDate.now());
			tesPagoValoresSelected.setTesBanco(new TesBanco());
			tesPagoValoresSelected.setIndEntregadoBoolean(false);
			tesPagoValoresSelected.setNroValor("0");
			tesPagoValoresSelected.setMontoValor(BigDecimal.ZERO);
			tesPagoValoresSelected.setBsEmpresa(new BsEmpresa());
			tesPagoValoresSelected.setBsTipoValor(new BsTipoValor());
			tesPagoValoresSelected.setTesPagoCabecera(new TesPagoCabecera());
		}
		return tesPagoValoresSelected;
	}

	public void setTesPagoValoresSelected(TesPagoValores tesPagoValoresSelected) {
		this.tesPagoValoresSelected = tesPagoValoresSelected;
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
			this.tesPagoCabecera.setBsTalonario(bsTalonarioSelected);
			bsTalonarioSelected = null;
		}
		this.bsTalonarioSelected = bsTalonarioSelected;
	}

	public CobCliente getCobClienteSelected() {
		if (Objects.isNull(cobClienteSelected)) {
			this.cobClienteSelected = new CobCliente();
			this.cobClienteSelected.setBsEmpresa(new BsEmpresa());
			this.cobClienteSelected.setBsPersona(new BsPersona());
		}
		return cobClienteSelected;
	}

	public void setCobClienteSelected(CobCliente cobClienteSelected) {
		if (!Objects.isNull(cobClienteSelected)) {
			this.tesPagoCabecera.setBeneficiario(cobClienteSelected.getBsPersona().getNombreCompleto());
			this.tesPagoCabecera.setIdBeneficiario(cobClienteSelected.getId());
			lazyModelDesembolso = null;
			getLazyModelDesembolso();
			PrimeFaces.current().ajax().update(":form:manageComprobante",":form:dt-desembolso", ":form:dt-comprobantes");
			//cobClienteSelected = null;
		}
		this.cobClienteSelected = cobClienteSelected;
	}

	public ComProveedor getComProveedorSelected() {
		if (Objects.isNull(comProveedorSelected)) {
			this.comProveedorSelected = new ComProveedor();
			this.comProveedorSelected.setBsEmpresa(new BsEmpresa());
			this.comProveedorSelected.setBsPersona(new BsPersona());
		}
		return comProveedorSelected;
	}

	public void setComProveedorSelected(ComProveedor comProveedorSelected) {
		this.comProveedorSelected = comProveedorSelected;
	}

	public List<TesPagoComprobanteDetalle> getTesPagoComprobanteDetallesList() {
		return tesPagoComprobanteDetallesList;
	}

	public void setTesPagoComprobanteDetallesList(List<TesPagoComprobanteDetalle> tesPagoComprobanteDetallesList) {
		this.tesPagoComprobanteDetallesList = tesPagoComprobanteDetallesList;
	}

	public List<TesPagoValores> getTesPagoValoresList() {
		return tesPagoValoresList;
	}

	public void setTesPagoValoresList(List<TesPagoValores> tesPagoValoresList) {
		this.tesPagoValoresList = tesPagoValoresList;
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

	public boolean isEstaAutorizado() {
		return estaAutorizado;
	}

	public void setEstaAutorizado(boolean estaAutorizado) {
		this.estaAutorizado = estaAutorizado;
	}

	public BigDecimal getMontoTotalPago() {
		return montoTotalPago;
	}

	public void setMontoTotalPago(BigDecimal montoTotalPago) {
		this.montoTotalPago = montoTotalPago;
	}

	public String getTipoSaldoAFiltrar() {
		return tipoSaldoAFiltrar;
	}

	public void setTipoSaldoAFiltrar(String tipoSaldoAFiltrar) {
		if (!StringUtils.isAllBlank(tipoSaldoAFiltrar)) {
			//lazyModelDesembolso = null;
			//getLazyModelDesembolso();
			// TODO:aca debo implementar cuando es proveedor traer saldos de compras
			//PrimeFaces.current().ajax().update(":form:manageComprobante", ":form:dt-comprobantes");
		}
		this.tipoSaldoAFiltrar = tipoSaldoAFiltrar;
	}

	public List<CreDesembolsoCabecera> getDesembolsoList() {
		return desembolsoList;
	}

	int a = 0;

	public void setDesembolsoList(List<CreDesembolsoCabecera> desembolsoList) {
		if (!Objects.isNull(desembolsoList)) {
			// TODO:hacer lo mismo cuando tengamos saldos de proveedores
			desembolsoList.stream().forEach((desembolso -> {
				TesPagoComprobanteDetalle detalle = new TesPagoComprobanteDetalle();
				detalle.setIdCuotaSaldo(desembolso.getId());
				detalle.setMontoPagado(desembolso.getMontoTotalCapital());
				detalle.setNroOrden(a++);
				detalle.setTipoComprobante("DESEMBOLSO");
				this.tesPagoComprobanteDetallesList.add(detalle);
			}));
		}

		this.desembolsoList = desembolsoList;
	}

	// LAZY
	public LazyDataModel<TesPagoCabecera> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<TesPagoCabecera>(this.tesPagoServiceImpl
					.buscarTesPagoCabeceraActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<TesPagoCabecera> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<CreDesembolsoCabecera> getLazyModelDesembolso() {
		if (Objects.isNull(lazyModelDesembolso) && !Objects.isNull(this.cobClienteSelected)) {
			List<CreDesembolsoCabecera> listaFiltrada = new ArrayList<CreDesembolsoCabecera>();
			if (!Objects.isNull(this.cobClienteSelected.getId())) {
				listaFiltrada = (List<CreDesembolsoCabecera>) creDesembolsoServiceImpl.buscarCreDesembolsoParaPagosTesoreriarLista(
						this.commonsUtilitiesController.getIdEmpresaLogueada(), this.cobClienteSelected.getId());
				int a = 0;
				System.out.println("DEBE ESTAR BIEN");
			}else {
				listaFiltrada = creDesembolsoServiceImpl.buscarCreDesembolsoCabeceraActivosLista(
						this.commonsUtilitiesController.getIdEmpresaLogueada());
				System.out.println("PASO POR ELSE");
			}
			lazyModelDesembolso = new GenericLazyDataModel<CreDesembolsoCabecera>(listaFiltrada);
		}
		return lazyModelDesembolso;
	}

	public void setLazyModelDesembolso(LazyDataModel<CreDesembolsoCabecera> lazyModelDesembolso) {
		this.lazyModelDesembolso = lazyModelDesembolso;
	}

	public LazyDataModel<BsTalonario> getLazyModelTalonario() {
		if (Objects.isNull(lazyModelTalonario)) {
			var moduloCredito = this.bsModuloServiceImpl.findByCodigo(Modulos.TESORERIA.getModulo());
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

	public LazyDataModel<ComProveedor> getLazyModelProveedor() {
		if (Objects.isNull(lazyModelProveedor)) {
			lazyModelProveedor = new GenericLazyDataModel<ComProveedor>((List<ComProveedor>) comProveedorServiceImpl
					.buscarComProveedorActivosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelProveedor;
	}

	public void setLazyModelProveedor(LazyDataModel<ComProveedor> lazyModelProveedor) {
		this.lazyModelProveedor = lazyModelProveedor;
	}

	public LazyDataModel<BsTipoValor> getLazyModelTipoValor() {
		if (Objects.isNull(lazyModelTipoValor)) {
			lazyModelTipoValor = new GenericLazyDataModel<BsTipoValor>((List<BsTipoValor>) bsTipoValorServiceImpl
					.buscarTipoValorActivosLista(sessionBean.getUsuarioLogueado().getBsEmpresa().getId()).stream()
					.filter(tipo -> tipo.getBsModulo().getCodigo().equalsIgnoreCase(Modulos.COBRANZAS.getModulo()))
					.collect(Collectors.toList()));
		}
		return lazyModelTipoValor;
	}

	public void setLazyModelTipoValor(LazyDataModel<BsTipoValor> lazyModelTipoValor) {
		this.lazyModelTipoValor = lazyModelTipoValor;
	}

	// SERVICES
	public TesPagoService getTesPagoServiceImpl() {
		return tesPagoServiceImpl;
	}

	public void setTesPagoServiceImpl(TesPagoService tesPagoServiceImpl) {
		this.tesPagoServiceImpl = tesPagoServiceImpl;
	}

	public CreDesembolsoService getCreDesembolsoServiceImpl() {
		return creDesembolsoServiceImpl;
	}

	public void setCreDesembolsoServiceImpl(CreDesembolsoService creDesembolsoServiceImpl) {
		this.creDesembolsoServiceImpl = creDesembolsoServiceImpl;
	}

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public CobClienteService getCobClienteServiceImpl() {
		return cobClienteServiceImpl;
	}

	public void setCobClienteServiceImpl(CobClienteService cobClienteServiceImpl) {
		this.cobClienteServiceImpl = cobClienteServiceImpl;
	}

	public BsTipoValorService getBsTipoValorServiceImpl() {
		return bsTipoValorServiceImpl;
	}

	public void setBsTipoValorServiceImpl(BsTipoValorService bsTipoValorServiceImpl) {
		this.bsTipoValorServiceImpl = bsTipoValorServiceImpl;
	}

	public ComProveedorService getComProveedorServiceImpl() {
		return comProveedorServiceImpl;
	}

	public void setComProveedorServiceImpl(ComProveedorService comProveedorServiceImpl) {
		this.comProveedorServiceImpl = comProveedorServiceImpl;
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

	public void validarCajaDelUsuario(boolean tieneHab) {
		if (tieneHab) {
			PrimeFaces.current().executeScript("PF('dlgNoTieneHabilitacion').show()");
			PrimeFaces.current().ajax().update("form:messages", "form:" + DT_NAME);
			return;
		}
	}

	public void redireccionarAHabilitaciones() {
		try {
			PrimeFaces.current().executeScript("PF('dlgNoTieneHabilitacion').hide()");
			CommonUtils.redireccionar("/pages/cliente/cobranzas/definicion/CobHabilitacionCaja.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Ocurrio un error al Guardar", System.err);
		}
	}

	public void onRowSelect(SelectEvent<TesPagoComprobanteDetalle> event) {
		this.tesPagoComprobanteDetalleSelected = event.getObject();
		this.calcularTotalesDetalle();
		this.tesPagoComprobanteDetalleSelected = null;
		// getCobCobrosValoresSelected();
		PrimeFaces.current().ajax().update(":form:dt-comprobantes", ":form:btnGuardar", ":form:btnAddComprobante");
	}

	public void onRowUnselect(UnselectEvent<TesPagoComprobanteDetalle> event) {
		this.tesPagoComprobanteDetalleSelected = event.getObject();
		this.calcularTotalesDetalle();
		this.tesPagoComprobanteDetalleSelected = null;
		// getCobCobrosValoresSelected();
		PrimeFaces.current().ajax().update(":form:dt-comprobantes", ":form:btnGuardar", ":form:btnAddComprobante");
	}

	public void calcularTotalesDetalle() {
		this.montoTotalPago = BigDecimal.ZERO;
		tesPagoCabecera.setMontoTotalPago(BigDecimal.ZERO);
		this.tesPagoComprobanteDetallesList.forEach(valor -> {
			montoTotalPago = montoTotalPago.add(valor.getMontoPagado());
		});
		tesPagoCabecera.setMontoTotalPago(montoTotalPago);

	}

	public void limpiarDetalleComprobantes() {
		this.tesPagoComprobanteDetallesList = new ArrayList<>();
		this.montoTotalPago = BigDecimal.ZERO;
		PrimeFaces.current().ajax().update(":form:dt-comprobantes", ":form:btnGuardar", ":form:btnAddComprobante",
				":form:dt-valores");
	}

	public void guardar() {
	}

	public void delete() {
	}

}
