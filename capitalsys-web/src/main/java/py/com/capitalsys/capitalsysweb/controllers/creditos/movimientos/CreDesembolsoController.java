package py.com.capitalsys.capitalsysweb.controllers.creditos.movimientos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import py.com.capitalsys.capitalsysentities.entities.creditos.CreDesembolsoDetalle;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreMotivoPrestamo;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;
import py.com.capitalsys.capitalsysentities.entities.creditos.CreTipoAmortizacion;
import py.com.capitalsys.capitalsysentities.entities.stock.StoArticulo;
import py.com.capitalsys.capitalsysentities.entities.ventas.VenVendedor;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.base.BsParametroService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreSolicitudCreditoService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreTipoAmortizacionService;
import py.com.capitalsys.capitalsysservices.services.stock.StoArticuloService;
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
public class CreDesembolsoController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(CreDesembolsoController.class);

	private CreDesembolsoCabecera creDesembolsoCabecera, creDesembolsoCabeceraSelected;
	private CreSolicitudCredito creSolicitudCreditoSelected;
	private CreTipoAmortizacion creTipoAmortizacionSelected;
	private BsTalonario bsTalonarioSelected;
	private StoArticulo stoArticuloSelected;
	private CreDesembolsoDetalle detalle;
	private Set<CreDesembolsoDetalle> detalleList;
	private LazyDataModel<CreDesembolsoCabecera> lazyModel;
	private LazyDataModel<CreSolicitudCredito> lazyModelSolicitudes;
	private LazyDataModel<CreTipoAmortizacion> lazyModelTipoAmortizacion;
	private LazyDataModel<BsTalonario> lazyModelTalonario;

	private List<String> estadoList;
	private boolean esNuegoRegistro;
	private boolean esVisibleFormulario = true;

	private static final String DT_NAME = "dt-desembolso";
	private static final String DT_DIALOG_NAME = "manageDesembolsoDialog";

	// services
	@ManagedProperty("#{creDesembolsoServiceImpl}")
	private CreDesembolsoService creDesembolsoServiceImpl;

	@ManagedProperty("#{creSolicitudCreditoServiceImpl}")
	private CreSolicitudCreditoService creSolicitudCreditoServiceImpl;

	@ManagedProperty("#{creTipoAmortizacionServiceImpl}")
	private CreTipoAmortizacionService creTipoAmortizacionServiceImpl;

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
		this.creDesembolsoCabecera = null;
		this.creDesembolsoCabeceraSelected = null;
		this.creSolicitudCreditoSelected = null;
		this.creTipoAmortizacionSelected = null;
		this.bsTalonarioSelected = null;
		this.stoArticuloSelected = null;
		this.detalle = null;

		this.lazyModel = null;
		this.lazyModelSolicitudes = null;
		this.lazyModelTipoAmortizacion = null;
		this.lazyModelTalonario = null;
		this.detalleList = new HashSet<CreDesembolsoDetalle>();

		this.esNuegoRegistro = true;
		this.esVisibleFormulario = !esVisibleFormulario;
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public CreDesembolsoCabecera getCreDesembolsoCabecera() {
		if (Objects.isNull(creDesembolsoCabecera)) {
			try {
				var moduloCredito = this.bsModuloServiceImpl.findByCodigo(Modulos.CREDITOS.getModulo());
				var tazaAnualValorParametrizado = this.commonsUtilitiesController.getValorParametro("TAZANUAL",
						moduloCredito.getId());
				var tazaMoraValorParametrizado = this.commonsUtilitiesController.getValorParametro("TAZAMORA",
						moduloCredito.getId());
				creDesembolsoCabecera = new CreDesembolsoCabecera();
				creDesembolsoCabecera.setFechaDesembolso(LocalDate.now());
				creDesembolsoCabecera.setEstado(Estado.ACTIVO.getEstado());
				creDesembolsoCabecera.setTazaAnual(new BigDecimal(tazaAnualValorParametrizado));
				creDesembolsoCabecera.setTazaMora(new BigDecimal(tazaMoraValorParametrizado));
				creDesembolsoCabecera.setBsEmpresa(new BsEmpresa());
				creDesembolsoCabecera.setBsTalonario(new BsTalonario());
				creDesembolsoCabecera.getBsTalonario().setBsTipoComprobante(new BsTipoComprobante());
				;
				creDesembolsoCabecera.setCreTipoAmortizacion(new CreTipoAmortizacion());
				creDesembolsoCabecera.setCreSolicitudCredito(new CreSolicitudCredito());
				creDesembolsoCabecera.getCreSolicitudCredito().setCobCliente(new CobCliente());
				creDesembolsoCabecera.getCreSolicitudCredito().getCobCliente().setBsPersona(new BsPersona());
				creDesembolsoCabecera.getCreSolicitudCredito().setVenVendedor(new VenVendedor());
				creDesembolsoCabecera.getCreSolicitudCredito().getVenVendedor().setBsPersona(new BsPersona());
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error con los parametros", e.getMessage());
				e.printStackTrace(System.err);
			}

		}
		return creDesembolsoCabecera;
	}

	public void setCreDesembolsoCabecera(CreDesembolsoCabecera creDesembolsoCabecera) {
		this.creDesembolsoCabecera = creDesembolsoCabecera;
	}

	public CreDesembolsoCabecera getCreDesembolsoCabeceraSelected() {
		if (Objects.isNull(creDesembolsoCabeceraSelected)) {
			creDesembolsoCabeceraSelected = new CreDesembolsoCabecera();
			creDesembolsoCabeceraSelected.setBsEmpresa(new BsEmpresa());
			creDesembolsoCabeceraSelected.setBsTalonario(new BsTalonario());
			creDesembolsoCabeceraSelected.setCreTipoAmortizacion(new CreTipoAmortizacion());
			creDesembolsoCabeceraSelected.setCreSolicitudCredito(new CreSolicitudCredito());
			creDesembolsoCabeceraSelected.getCreSolicitudCredito().setCobCliente(new CobCliente());
			creDesembolsoCabeceraSelected.getCreSolicitudCredito().getCobCliente().setBsPersona(new BsPersona());
			creDesembolsoCabeceraSelected.getCreSolicitudCredito().setVenVendedor(new VenVendedor());
			creDesembolsoCabeceraSelected.getCreSolicitudCredito().getVenVendedor().setBsPersona(new BsPersona());
			creDesembolsoCabeceraSelected.getCreSolicitudCredito().setCreMotivoPrestamo(new CreMotivoPrestamo());

		}
		return creDesembolsoCabeceraSelected;
	}

	public void setCreDesembolsoCabeceraSelected(CreDesembolsoCabecera creDesembolsoCabeceraSelected) {
		if (!Objects.isNull(creDesembolsoCabeceraSelected)) {
			creDesembolsoCabecera = creDesembolsoCabeceraSelected;
			creDesembolsoCabeceraSelected = null;
			this.esNuegoRegistro = false;
			this.esVisibleFormulario = true;
		}
		this.creDesembolsoCabeceraSelected = creDesembolsoCabeceraSelected;
	}

	public CreDesembolsoDetalle getDetalle() {
		if (Objects.isNull(detalle)) {
			detalle = new CreDesembolsoDetalle();
			detalle.setCreDesembolsoCabecera(new CreDesembolsoCabecera());
			detalle.setStoArticulo(new StoArticulo());
		}
		return detalle;
	}

	public void setDetalle(CreDesembolsoDetalle detalle) {
		this.detalle = detalle;
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
			this.creDesembolsoCabecera.setBsTalonario(bsTalonarioSelected);
			bsTalonarioSelected = null;
		}
		this.bsTalonarioSelected = bsTalonarioSelected;
	}

	public CreSolicitudCredito getCreSolicitudCreditoSelected() {
		if (Objects.isNull(creSolicitudCreditoSelected)) {
			this.creSolicitudCreditoSelected = new CreSolicitudCredito();
			this.creSolicitudCreditoSelected.setFechaSolicitud(LocalDate.now());
			this.creSolicitudCreditoSelected.setPrimerVencimiento(LocalDate.now());
			this.creSolicitudCreditoSelected.setBsEmpresa(new BsEmpresa());
			this.creSolicitudCreditoSelected.setCobCliente(new CobCliente());
			this.creSolicitudCreditoSelected.getCobCliente().setBsPersona(new BsPersona());
			this.creSolicitudCreditoSelected.setVenVendedor(new VenVendedor());
			this.creSolicitudCreditoSelected.getVenVendedor().setBsPersona(new BsPersona());
			this.creSolicitudCreditoSelected.setCreMotivoPrestamo(new CreMotivoPrestamo());
		}
		return creSolicitudCreditoSelected;
	}

	public void setCreSolicitudCreditoSelected(CreSolicitudCredito creSolicitudCreditoSelected) {
		if (!Objects.isNull(creSolicitudCreditoSelected)) {
			this.creDesembolsoCabecera.setCreSolicitudCredito(creSolicitudCreditoSelected);
			creSolicitudCreditoSelected = null;
		}
		this.creSolicitudCreditoSelected = creSolicitudCreditoSelected;
	}

	public CreTipoAmortizacion getCreTipoAmortizacionSelected() {
		if (Objects.isNull(creTipoAmortizacionSelected)) {
			this.creTipoAmortizacionSelected = new CreTipoAmortizacion();
			this.creTipoAmortizacionSelected.setEstado(Estado.ACTIVO.getEstado());
		}
		return creTipoAmortizacionSelected;
	}

	public void setCreTipoAmortizacionSelected(CreTipoAmortizacion creTipoAmortizacionSelected) {
		if (!Objects.isNull(creTipoAmortizacionSelected)) {
			this.creDesembolsoCabecera.setCreTipoAmortizacion(creTipoAmortizacionSelected);
			creTipoAmortizacionSelected = null;
		}
		this.creTipoAmortizacionSelected = creTipoAmortizacionSelected;
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
		this.stoArticuloSelected = stoArticuloSelected;
	}

	public CreDesembolsoService getCreDesembolsoServiceImpl() {
		return creDesembolsoServiceImpl;
	}

	public void setCreDesembolsoServiceImpl(CreDesembolsoService creDesembolsoServiceImpl) {
		this.creDesembolsoServiceImpl = creDesembolsoServiceImpl;
	}

	public CreSolicitudCreditoService getCreSolicitudCreditoServiceImpl() {
		return creSolicitudCreditoServiceImpl;
	}

	public void setCreSolicitudCreditoServiceImpl(CreSolicitudCreditoService creSolicitudCreditoServiceImpl) {
		this.creSolicitudCreditoServiceImpl = creSolicitudCreditoServiceImpl;
	}

	public CreTipoAmortizacionService getCreTipoAmortizacionServiceImpl() {
		return creTipoAmortizacionServiceImpl;
	}

	public void setCreTipoAmortizacionServiceImpl(CreTipoAmortizacionService creTipoAmortizacionServiceImpl) {
		this.creTipoAmortizacionServiceImpl = creTipoAmortizacionServiceImpl;
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

	public CommonsUtilitiesController getCommonsUtilitiesController() {
		return commonsUtilitiesController;
	}

	public void setCommonsUtilitiesController(CommonsUtilitiesController commonsUtilitiesController) {
		this.commonsUtilitiesController = commonsUtilitiesController;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
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

	public StoArticuloService getStoArticuloServiceImpl() {
		return stoArticuloServiceImpl;
	}

	public void setStoArticuloServiceImpl(StoArticuloService stoArticuloServiceImpl) {
		this.stoArticuloServiceImpl = stoArticuloServiceImpl;
	}

	public boolean isEsVisibleFormulario() {
		return esVisibleFormulario;
	}

	public void setEsVisibleFormulario(boolean esVisibleFormulario) {
		this.cleanFields();
		this.esVisibleFormulario = esVisibleFormulario;
	}

	public LazyDataModel<CreDesembolsoCabecera> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<CreDesembolsoCabecera>(
					(List<CreDesembolsoCabecera>) creDesembolsoServiceImpl.buscarCreDesembolsoCabeceraActivosLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<CreDesembolsoCabecera> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<CreSolicitudCredito> getLazyModelSolicitudes() {
		if (Objects.isNull(lazyModelSolicitudes)) {
			lazyModelSolicitudes = new GenericLazyDataModel<CreSolicitudCredito>(
					(List<CreSolicitudCredito>) creSolicitudCreditoServiceImpl
							.buscarSolicitudAutorizadosLista(this.commonsUtilitiesController.getIdEmpresaLogueada()));
		}
		return lazyModelSolicitudes;
	}

	public void setLazyModelSolicitudes(LazyDataModel<CreSolicitudCredito> lazyModelSolicitudes) {
		this.lazyModelSolicitudes = lazyModelSolicitudes;
	}

	public LazyDataModel<BsTalonario> getLazyModelTalonario() {
		if (Objects.isNull(lazyModelTalonario)) {
			var moduloCredito = this.bsModuloServiceImpl.findByCodigo(Modulos.CREDITOS.getModulo());
			lazyModelTalonario = new GenericLazyDataModel<BsTalonario>(
					this.commonsUtilitiesController.bsTalonarioPorModuloLista(
							this.commonsUtilitiesController.getIdEmpresaLogueada(), moduloCredito.getId()));

		}
		return lazyModelTalonario;
	}

	public void setLazyModelTalonario(LazyDataModel<BsTalonario> lazyModelTalonario) {
		this.lazyModelTalonario = lazyModelTalonario;
	}

	public LazyDataModel<CreTipoAmortizacion> getLazyModelTipoAmortizacion() {
		if (Objects.isNull(lazyModelTipoAmortizacion)) {
			lazyModelTipoAmortizacion = new GenericLazyDataModel<CreTipoAmortizacion>(
					creTipoAmortizacionServiceImpl.buscarCreTipoAmortizacionActivosLista());
		}
		return lazyModelTipoAmortizacion;
	}

	public void setLazyModelTipoAmortizacion(LazyDataModel<CreTipoAmortizacion> lazyModelTipoAmortizacion) {
		this.lazyModelTipoAmortizacion = lazyModelTipoAmortizacion;
	}

	public Set<CreDesembolsoDetalle> getDetalleList() {
		return detalleList;
	}

	public void setDetalleList(Set<CreDesembolsoDetalle> detalleList) {
		this.detalleList = detalleList;
	}

	public void procesarCuotas() {
		if (Objects.isNull(creDesembolsoCabecera.getCreTipoAmortizacion())
				|| Objects.isNull(creDesembolsoCabecera.getCreTipoAmortizacion().getId())) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					"Debe seleccionar un tipo de Amortizacion.");
			PrimeFaces.current().ajax().update(":form:messages");
			return;
		}
		switch (creDesembolsoCabecera.getCreTipoAmortizacion().getCodTipo()) {
		case "AME":
			generarCuotasMetodoAmericano();
			break;
		case "FRA":
			generarCuotasMetodoFrances();
			break;
		case "ALE":
			generarCuotasMetodoAleman();
			break;

		default:
			generarCuotasNormal();
			break;
		}
	}

	private void generarCuotasNormal() {
		limpiarCuotas();
		try {
			try {
				this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("CUO",
						this.commonsUtilitiesController.getIdEmpresaLogueada());
			} catch (Exception e) {
				LOGGER.error("Ocurrio un error al generar cuotas", e);
				e.printStackTrace(System.err);
			}
			if (CollectionUtils.isEmpty(creDesembolsoCabecera.getCreDesembolsoDetalleList())) {
				BigDecimal porcAnual = creDesembolsoCabecera.getTazaAnual().divide(BigDecimal.valueOf(100));
				BigDecimal montoSolicitado = creDesembolsoCabecera.getCreSolicitudCredito().getMontoAprobado();
				BigDecimal interes = montoSolicitado.multiply(porcAnual);
				BigDecimal montoConInteres = montoSolicitado.add(interes);
				creDesembolsoCabecera.setCreDesembolsoDetalleList(new ArrayList<CreDesembolsoDetalle>());

				int plazo = creDesembolsoCabecera.getCreSolicitudCredito().getPlazo();

				BigDecimal montoXcuota = montoSolicitado.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
				BigDecimal cuota = montoConInteres.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
				BigDecimal interesXcuota = interes.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
				BigDecimal ivaInteresXcuota = interesXcuota.divide(BigDecimal.valueOf(11), 2, RoundingMode.UP);

				BigDecimal totalCapital = BigDecimal.ZERO;
				BigDecimal totalInteres = BigDecimal.ZERO;
				BigDecimal totalIVA = BigDecimal.ZERO;
				BigDecimal totalCuota = BigDecimal.ZERO;

				LocalDate fechaVencimiento = creDesembolsoCabecera.getCreSolicitudCredito().getPrimerVencimiento();

				for (int i = 1; i <= creDesembolsoCabecera.getCreSolicitudCredito().getPlazo(); i++) {
					detalle = new CreDesembolsoDetalle();
					// detalle.setCreDesembolsoCabecera(creDesembolsoCabecera);
					detalle.setMontoCapital(montoXcuota);
					detalle.setMontoCuota(cuota);
					detalle.setMontoInteres(interesXcuota.subtract(ivaInteresXcuota));
					detalle.setMontoIva(ivaInteresXcuota);
					detalle.setNroCuota(i);
					detalle.setCantidad(1);
					detalle.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
					detalle.setEstado(Estado.ACTIVO.getEstado());
					detalle.setFechaVencimiento(fechaVencimiento);
					fechaVencimiento = fechaVencimiento.plusMonths(1);
					detalle.setStoArticulo(this.stoArticuloSelected);

					totalCapital = totalCapital.add(montoXcuota);
					totalInteres = totalInteres.add(interesXcuota);
					totalIVA = totalIVA.add(ivaInteresXcuota);
					totalCuota = totalCuota.add(cuota);

					creDesembolsoCabecera.addDetalle(detalle);
				}
				creDesembolsoCabecera.setCabeceraADetalle();
				creDesembolsoCabecera.calcularTotales();
				PrimeFaces.current().ajax().update(":form:dt-detalle");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getMessage().substring(0, e.getMessage().length()) + "...");
			PrimeFaces.current().ajax().update(":form:messages");
		}

	}

	private void generarCuotasMetodoFrances() {
	    try {
	        this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("CUO",
	                this.commonsUtilitiesController.getIdEmpresaLogueada());

	        if (CollectionUtils.isEmpty(creDesembolsoCabecera.getCreDesembolsoDetalleList())) {
	            creDesembolsoCabecera.setCreDesembolsoDetalleList(new ArrayList<CreDesembolsoDetalle>());
	            double monto = creDesembolsoCabecera.getCreSolicitudCredito().getMontoAprobado().doubleValue();
	            double tasaAnual = creDesembolsoCabecera.getTazaAnual().divide(BigDecimal.valueOf(100)).doubleValue();
	            double plazoMeses = creDesembolsoCabecera.getCreSolicitudCredito().getPlazo();
	            double iva = this.stoArticuloSelected.getBsIva().getPorcentaje().divide(BigDecimal.valueOf(100)).doubleValue();
	            
	            //////
	            double tasaMensual = tasaAnual / 12;
	            double cuota = (monto * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -plazoMeses));
	            double intereses;
	            double capital;
	            double saldo = monto;
	            

	            LocalDate fechaVencimiento = creDesembolsoCabecera.getCreSolicitudCredito().getPrimerVencimiento();

	            for (int i = 1; i <= plazoMeses; i++) {
	            	
	            	intereses = saldo * tasaMensual;
	                capital = cuota - intereses;
	                saldo -= capital;
	                double montoIva = cuota * iva;
	                double cuotaConIva = cuota + montoIva;
	                
	                
	                CreDesembolsoDetalle detalle = new CreDesembolsoDetalle();

	                detalle.setMontoCapital(BigDecimal.valueOf(capital));
	                detalle.setMontoInteres(BigDecimal.valueOf(intereses));
	                detalle.setMontoIva(BigDecimal.valueOf(montoIva));
	                detalle.setMontoCuota(BigDecimal.valueOf(cuotaConIva));
	                detalle.setNroCuota(i);
	                detalle.setCantidad(1);
	                detalle.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
	                detalle.setEstado(Estado.ACTIVO.getEstado());
	                detalle.setFechaVencimiento(fechaVencimiento);
	                fechaVencimiento = fechaVencimiento.plusMonths(1);
	                detalle.setStoArticulo(this.stoArticuloSelected);

	                creDesembolsoCabecera.addDetalle(detalle);
	            }

	            creDesembolsoCabecera.setCabeceraADetalle();
	            creDesembolsoCabecera.calcularTotales();
	            PrimeFaces.current().ajax().update(":form:dt-detalle");
	        }
	    } catch (Exception e) {
	        LOGGER.error("Ocurrió un error al generar cuotas METODO FRANCES", e);
	        e.printStackTrace(System.err);
	        CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
	                e.getMessage().substring(0, e.getMessage().length()) + "...");
	        PrimeFaces.current().ajax().update(":form:messages");
	    }
	}

	private void generarCuotasMetodoAleman() {
		limpiarCuotas();
		try {
			this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("CUO",
					this.commonsUtilitiesController.getIdEmpresaLogueada());

			if (CollectionUtils.isEmpty(creDesembolsoCabecera.getCreDesembolsoDetalleList())) {
				BigDecimal montoSolicitado = creDesembolsoCabecera.getCreSolicitudCredito().getMontoAprobado();
				BigDecimal porcAnual = creDesembolsoCabecera.getTazaAnual().divide(BigDecimal.valueOf(100));
				int plazo = creDesembolsoCabecera.getCreSolicitudCredito().getPlazo();

				BigDecimal tasaMensual = porcAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
				BigDecimal cuotaCapital = montoSolicitado.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.HALF_UP);

				BigDecimal totalCapital = BigDecimal.ZERO;
				BigDecimal totalInteres = BigDecimal.ZERO;
				BigDecimal totalIVA = BigDecimal.ZERO;
				BigDecimal totalCuota = BigDecimal.ZERO;

				LocalDate fechaVencimiento = creDesembolsoCabecera.getCreSolicitudCredito().getPrimerVencimiento();

				for (int i = 1; i <= plazo; i++) {
					CreDesembolsoDetalle detalle = new CreDesembolsoDetalle();

					BigDecimal interes = montoSolicitado.multiply(tasaMensual);
					BigDecimal ivaInteres = interes.divide(BigDecimal.valueOf(11), 2, RoundingMode.HALF_UP);

					detalle.setMontoCapital(cuotaCapital);
					detalle.setMontoInteres(interes.subtract(ivaInteres));
					detalle.setMontoIva(ivaInteres);
					detalle.setMontoCuota(cuotaCapital.add(interes));

					detalle.setNroCuota(i);
					detalle.setCantidad(1);
					detalle.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
					detalle.setEstado(Estado.ACTIVO.getEstado());
					detalle.setFechaVencimiento(fechaVencimiento);
					fechaVencimiento = fechaVencimiento.plusMonths(1);
					detalle.setStoArticulo(this.stoArticuloSelected);

					totalCapital = totalCapital.add(cuotaCapital);
					totalInteres = totalInteres.add(interes);
					totalIVA = totalIVA.add(ivaInteres);
					totalCuota = totalCuota.add(detalle.getMontoCuota());

					creDesembolsoCabecera.addDetalle(detalle);

					montoSolicitado = montoSolicitado.subtract(cuotaCapital);
				}

				creDesembolsoCabecera.setCabeceraADetalle();
				creDesembolsoCabecera.calcularTotales();
				PrimeFaces.current().ajax().update(":form:dt-detalle");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al generar cuotas", e);
			e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getMessage().substring(0, e.getMessage().length()) + "...");
			PrimeFaces.current().ajax().update(":form:messages");
		}
	}

	private void generarCuotasMetodoAmericano() {
		limpiarCuotas();
		try {
			this.stoArticuloSelected = this.stoArticuloServiceImpl.buscarArticuloPorCodigo("CUO",
					this.commonsUtilitiesController.getIdEmpresaLogueada());

			if (CollectionUtils.isEmpty(creDesembolsoCabecera.getCreDesembolsoDetalleList())) {
				BigDecimal montoSolicitado = creDesembolsoCabecera.getCreSolicitudCredito().getMontoAprobado();
				BigDecimal porcAnual = creDesembolsoCabecera.getTazaAnual().divide(BigDecimal.valueOf(100));
				int plazo = creDesembolsoCabecera.getCreSolicitudCredito().getPlazo();

				BigDecimal tasaMensual = porcAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
				BigDecimal cuotaInteres = montoSolicitado.multiply(tasaMensual);
				BigDecimal cuotaPrincipal = montoSolicitado.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.HALF_UP);

				BigDecimal totalCapital = BigDecimal.ZERO;
				BigDecimal totalInteres = BigDecimal.ZERO;
				BigDecimal totalIVA = BigDecimal.ZERO;
				BigDecimal totalCuota = BigDecimal.ZERO;

				LocalDate fechaVencimiento = creDesembolsoCabecera.getCreSolicitudCredito().getPrimerVencimiento();

				for (int i = 1; i <= plazo; i++) {
					CreDesembolsoDetalle detalle = new CreDesembolsoDetalle();

					detalle.setMontoCapital(cuotaPrincipal);
					detalle.setMontoInteres(cuotaInteres);
					detalle.setMontoIva(cuotaInteres.divide(BigDecimal.valueOf(11), 2, RoundingMode.HALF_UP));
					detalle.setMontoCuota(cuotaPrincipal.add(cuotaInteres));

					detalle.setNroCuota(i);
					detalle.setCantidad(1);
					detalle.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
					detalle.setEstado(Estado.ACTIVO.getEstado());
					detalle.setFechaVencimiento(fechaVencimiento);
					fechaVencimiento = fechaVencimiento.plusMonths(1);
					detalle.setStoArticulo(this.stoArticuloSelected);

					totalCapital = totalCapital.add(cuotaPrincipal);
					totalInteres = totalInteres.add(cuotaInteres);
					totalIVA = totalIVA.add(detalle.getMontoIva());
					totalCuota = totalCuota.add(detalle.getMontoCuota());

					creDesembolsoCabecera.addDetalle(detalle);
				}

				creDesembolsoCabecera.setCabeceraADetalle();
				creDesembolsoCabecera.calcularTotales();
				PrimeFaces.current().ajax().update(":form:dt-detalle");
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al generar cuotas", e);
			e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getMessage().substring(0, e.getMessage().length()) + "...");
			PrimeFaces.current().ajax().update(":form:messages");
		}
	}

	public void limpiarCuotas() {
		creDesembolsoCabecera.setCreDesembolsoDetalleList(new ArrayList<CreDesembolsoDetalle>());
		creDesembolsoCabecera.setMontoTotalCapital(BigDecimal.ZERO);
		creDesembolsoCabecera.setMontoTotalInteres(BigDecimal.ZERO);
		creDesembolsoCabecera.setMontoTotalIva(BigDecimal.ZERO);
		creDesembolsoCabecera.setMontoTotalCredito(BigDecimal.ZERO);
		PrimeFaces.current().ajax().update(":form:btnLimpiar");
	}

	public void guardar() {
		try {
			if (Objects.isNull(this.creDesembolsoCabecera.getCreSolicitudCredito())
					|| Objects.isNull(this.creDesembolsoCabecera.getCreSolicitudCredito().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Debe seleccionar una Solicitud.");
				return;
			}
			if (Objects.isNull(this.creDesembolsoCabecera.getCreTipoAmortizacion())
					|| Objects.isNull(this.creDesembolsoCabecera.getCreTipoAmortizacion().getId())) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"Debe seleccionar un tipo de Amortizacion.");
				return;
			}
			if (CollectionUtils.isEmpty(creDesembolsoCabecera.getCreDesembolsoDetalleList())
					|| creDesembolsoCabecera.getCreDesembolsoDetalleList().size() == 0) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
						"Debe generar las Cuotas para guardar.");
				return;
			}
			this.creDesembolsoCabecera.setUsuarioModificacion(sessionBean.getUsuarioLogueado().getCodUsuario());
			this.creDesembolsoCabecera.setBsEmpresa(sessionBean.getUsuarioLogueado().getBsEmpresa());
			this.creDesembolsoCabecera.setIndFacturado("N");
			if (Objects.isNull(this.creDesembolsoCabecera.getId())) {
				this.creDesembolsoCabecera.setNroDesembolso(this.creDesembolsoServiceImpl
						.calcularNroDesembolsoDisponible(commonsUtilitiesController.getIdEmpresaLogueada()));
			}
			if (!Objects.isNull(this.creDesembolsoServiceImpl.save(creDesembolsoCabecera))) {
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
							"La solicitud ya fue desembolsado.");
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
			if (Objects.isNull(this.creDesembolsoCabecera.getCreSolicitudCredito())
					|| this.creDesembolsoCabecera.isIndDesembolsadoBoolean()) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "Ya fue desembolsado.");
				return;
			}
			if (!Objects.isNull(this.creDesembolsoCabecera)) {
				this.creDesembolsoServiceImpl.deleteById(this.creDesembolsoCabecera.getId());
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
