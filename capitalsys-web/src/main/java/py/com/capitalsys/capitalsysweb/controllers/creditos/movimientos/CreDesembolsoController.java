package py.com.capitalsys.capitalsysweb.controllers.creditos.movimientos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import py.com.capitalsys.capitalsysservices.services.base.BsTalonarioService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreDesembolsoService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreSolicitudCreditoService;
import py.com.capitalsys.capitalsysservices.services.creditos.CreTipoAmortizacionService;
import py.com.capitalsys.capitalsysservices.services.stock.StoArticuloService;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

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
	private boolean esVisibleFormulario;

	private static final String DT_NAME = "dt-desembolso";
	private static final String DT_DIALOG_NAME = "manageDesembolsoDialog";

	// services
	@ManagedProperty("#{creDesembolsoServiceImpl}")
	private CreDesembolsoService creDesembolsoServiceImpl;

	@ManagedProperty("#{creSolicitudCreditoServiceImpl}")
	private CreSolicitudCreditoService creSolicitudCreditoServiceImpl;

	@ManagedProperty("#{creTipoAmortizacionServiceImpl}")
	private CreTipoAmortizacionService creTipoAmortizacionServiceImpl;

	@ManagedProperty("#{bsTalonarioServiceImpl}")
	private BsTalonarioService bsTalonarioServiceImpl;

	@ManagedProperty("#{stoArticuloServiceImpl}")
	private StoArticuloService stoArticuloServiceImpl;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

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
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS Y SETTERS
	public CreDesembolsoCabecera getCreDesembolsoCabecera() {
		if (Objects.isNull(creDesembolsoCabecera)) {
			creDesembolsoCabecera = new CreDesembolsoCabecera();
			creDesembolsoCabecera.setFechaDesembolso(LocalDate.now());
			creDesembolsoCabecera.setBsEmpresa(new BsEmpresa());
			creDesembolsoCabecera.setBsTalonario(new BsTalonario());
			creDesembolsoCabecera.setCreTipoAmortizacion(new CreTipoAmortizacion());
			creDesembolsoCabecera.setCreSolicitudCredito(new CreSolicitudCredito());
			creDesembolsoCabecera.getCreSolicitudCredito().setCobCliente(new CobCliente());
			creDesembolsoCabecera.getCreSolicitudCredito().getCobCliente().setBsPersona(new BsPersona());
			creDesembolsoCabecera.getCreSolicitudCredito().setVenVendedor(new VenVendedor());
			creDesembolsoCabecera.getCreSolicitudCredito().getVenVendedor().setBsPersona(new BsPersona());
			creDesembolsoCabecera.getCreSolicitudCredito().setCreMotivoPrestamo(new CreMotivoPrestamo());

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

	public BsTalonarioService getBsTalonarioServiceImpl() {
		return bsTalonarioServiceImpl;
	}

	public void setBsTalonarioServiceImpl(BsTalonarioService bsTalonarioServiceImpl) {
		this.bsTalonarioServiceImpl = bsTalonarioServiceImpl;
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
					(List<CreDesembolsoCabecera>) creDesembolsoServiceImpl
							.buscarCreDesembolsoCabeceraActivosLista(sessionBean.getUsuarioLogueado().getId()));
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
							.buscarCobradorActivosLista(sessionBean.getUsuarioLogueado().getId()));
		}
		return lazyModelSolicitudes;
	}

	public void setLazyModelSolicitudes(LazyDataModel<CreSolicitudCredito> lazyModelSolicitudes) {
		this.lazyModelSolicitudes = lazyModelSolicitudes;
	}

	public LazyDataModel<BsTalonario> getLazyModelTalonario() {
		if (Objects.isNull(lazyModelTalonario)) {
			lazyModelTalonario = new GenericLazyDataModel<BsTalonario>((List<BsTalonario>) bsTalonarioServiceImpl
					.buscarBsTalonarioActivosLista(sessionBean.getUsuarioLogueado().getId()));
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

	public void generarCuotas() {
		try {
		    if (detalleList == null || detalleList.isEmpty()) {
		        BigDecimal porcAnual = creDesembolsoCabecera.getTazaAnual().divide(BigDecimal.valueOf(100));
		        BigDecimal montoSolicitado = creDesembolsoCabecera.getCreSolicitudCredito().getMontoSolicitado();
		        BigDecimal interes = montoSolicitado.multiply(porcAnual);
		        BigDecimal montoConInteres = montoSolicitado.add(interes);
		        
		        int plazo = creDesembolsoCabecera.getCreSolicitudCredito().getPlazo();

		        BigDecimal montoXcuota = montoSolicitado.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
		        BigDecimal cuota = montoConInteres.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
		        BigDecimal interesXcuota = interes.divide(BigDecimal.valueOf(plazo), 2, RoundingMode.UP);
		        BigDecimal ivaInteresXcuota = interesXcuota.divide(BigDecimal.valueOf(11), 2, RoundingMode.UP);

		        BigDecimal totalCapital = BigDecimal.ZERO;
		        BigDecimal totalInteres = BigDecimal.ZERO;
		        BigDecimal totalIVA = BigDecimal.ZERO;
		        BigDecimal totalCuota = BigDecimal.ZERO;

		        this.detalleList = new HashSet<>();

		        LocalDate fechaVencimiento = creDesembolsoCabecera.getCreSolicitudCredito().getPrimerVencimiento();

		        for (int i = 1; i <= creDesembolsoCabecera.getCreSolicitudCredito().getPlazo(); i++) {
		            detalle = new CreDesembolsoDetalle();
		            detalle.setCreDesembolsoCabecera(creDesembolsoCabecera);
		            detalle.setMontoCapital(montoXcuota);
		            detalle.setMontoCuota(cuota);
		            detalle.setMontoInteres(interesXcuota.subtract(ivaInteresXcuota));
		            detalle.setMontoIva(ivaInteresXcuota);
		            detalle.setNroCuota(i);
		            detalle.setFechaVencimiento(fechaVencimiento);
		            fechaVencimiento = fechaVencimiento.plusMonths(1);

		            totalCapital = totalCapital.add(montoXcuota);
		            totalInteres = totalInteres.add(interesXcuota);
		            totalIVA = totalIVA.add(ivaInteresXcuota);
		            totalCuota = totalCuota.add(cuota);

		            detalleList.add(detalle);
		        }
		    }
		    var a = 0;
		} catch (Exception e) {
		    LOGGER.error("Ocurrio un error al generar cuotas", e);
		    CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", e.getMessage().length() + "...");
		}


	}

}
