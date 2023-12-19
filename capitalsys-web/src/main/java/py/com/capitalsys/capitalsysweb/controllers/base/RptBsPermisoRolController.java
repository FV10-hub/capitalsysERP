package py.com.capitalsys.capitalsysweb.controllers.base;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import py.com.capitalsys.capitalsysentities.dto.ParametrosReporte;
import py.com.capitalsys.capitalsysweb.session.SessionBean;
import py.com.capitalsys.capitalsysweb.utils.ApplicationConstant;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.GenerarReporte;
import py.com.capitalsys.capitalsysweb.utils.Modulos;

/*
* 15 dic. 2023 - Elitebook
*/
@ManagedBean
@ViewScoped
public class RptBsPermisoRolController {

	private ParametrosReporte parametrosReporte;

	private LocalDate fecDesde;
	private LocalDate fecHasta;

	@ManagedProperty("#{generarReporte}")
	private GenerarReporte generarReporte;

	/**
	 * Propiedad de la logica de negocio inyectada con JSF y Spring.
	 */
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;

	public ParametrosReporte getParametrosReporte() {
		if (Objects.isNull(parametrosReporte)) {
			parametrosReporte = new ParametrosReporte();
			parametrosReporte.setCodModulo(Modulos.BASE.getModulo());
			parametrosReporte.setReporte("BsPermisoRolPorFecha");
		}
		return parametrosReporte;
	}

	public void setParametrosReporte(ParametrosReporte parametrosReporte) {
		this.parametrosReporte = parametrosReporte;
	}

	public GenerarReporte getGenerarReporte() {
		return generarReporte;
	}

	public void setGenerarReporte(GenerarReporte generarReporte) {
		this.generarReporte = generarReporte;
	}

	public LocalDate getFecDesde() {
		if(Objects.isNull(fecDesde))
			fecDesde = LocalDate.now();
		return fecDesde;
	}

	public void setFecDesde(LocalDate fecDesde) {
		this.fecDesde = fecDesde;
	}

	public LocalDate getFecHasta() {
		if(Objects.isNull(fecHasta))
			fecHasta = LocalDate.now();
		return fecHasta;
	}

	public void setFecHasta(LocalDate fecHasta) {
		this.fecHasta = fecHasta;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public void downloadReport() {
		this.prepareParams();
		if (!(Objects.isNull(parametrosReporte) && Objects.isNull(parametrosReporte.getFormato()))
				&& CollectionUtils.isNotEmpty(this.parametrosReporte.getParametros())
				&& CollectionUtils.isNotEmpty(this.parametrosReporte.getValores())) {
			this.generarReporte.descargarReporte(parametrosReporte);
		} else {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡CUIDADO!",
					"Debes seccionar los parametros validos.");
		}

	}

	/*
	 * Recordar que el orden en la que se agregan los valores en las listas SI
	 * importan ya que en el backend se procesa como llave valor y va ir pareando en
	 * el mismo orden
	 */
	private void prepareParams() {
		// basicos
		// Obtener la fecha y hora actual
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatterDiaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTimeDiaHora = now.format(formatterDiaHora);
		this.parametrosReporte.getParametros().add(ApplicationConstant.REPORT_PARAM_IMAGEN_PATH);
		this.parametrosReporte.getParametros().add(ApplicationConstant.REPORT_PARAM_NOMBRE_IMAGEN);
		this.parametrosReporte.getParametros().add(ApplicationConstant.REPORT_PARAM_IMPRESO_POR);
		this.parametrosReporte.getParametros().add(ApplicationConstant.REPORT_PARAM_DIA_HORA);
		this.parametrosReporte.getParametros().add(ApplicationConstant.REPORT_PARAM_DESC_EMPRESA);

		this.parametrosReporte.getValores().add(ApplicationConstant.PATH_IMAGEN_EMPRESA);
		this.parametrosReporte.getValores().add(ApplicationConstant.IMAGEN_EMPRESA_NAME);
		this.parametrosReporte.getValores()
				.add(this.sessionBean.getUsuarioLogueado().getBsPersona().getNombreCompleto());
		this.parametrosReporte.getValores().add(formattedDateTimeDiaHora);
		this.parametrosReporte.getValores()
				.add(this.sessionBean.getUsuarioLogueado().getBsEmpresa().getNombreFantasia());
		// basico

		DateTimeFormatter formatToDateParam = DateTimeFormatter.ofPattern("dd/MM/yyy");
		// key
		this.parametrosReporte.getParametros().add("p_fecha_inicio");
		this.parametrosReporte.getParametros().add("p_fecha_fin");

		// values
		this.parametrosReporte.getValores().add(fecDesde.format(formatToDateParam));
		this.parametrosReporte.getValores().add(fecHasta.format(formatToDateParam));

	}

}
