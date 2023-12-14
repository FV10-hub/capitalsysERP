/**
 * 
 */
package py.com.capitalsys.capitalsysweb.controllers.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import py.com.capitalsys.capitalsysentities.dto.ParametrosReporte;
import py.com.capitalsys.capitalsysentities.entities.base.BsModulo;
import py.com.capitalsys.capitalsysservices.services.base.BsModuloService;
import py.com.capitalsys.capitalsysservices.services.client.ReportesServiceClient;
import py.com.capitalsys.capitalsysservices.services.client.ReportesServiceClientImpl;
import py.com.capitalsys.capitalsysweb.utils.CommonUtils;
import py.com.capitalsys.capitalsysweb.utils.Estado;
import py.com.capitalsys.capitalsysweb.utils.GenericLazyDataModel;

/**
 * descomentar si por algun motivo se necesita trabajar directo con spring
 * //@Component y // @Autowired
 */
@ManagedBean
@ViewScoped
//@Component
public class BsModuloController {

	/**
	 * Objeto que permite mostrar los mensajes de LOG en la consola del servidor o
	 * en un archivo externo.
	 */
	private static final Logger LOGGER = LogManager.getLogger(BsModuloController.class);

	private BsModulo bsModulo, bsModuloSelected;
	private LazyDataModel<BsModulo> lazyModel;
	private List<String> estadoList;
	private boolean esNuegoRegistro;

	@ManagedProperty("#{bsModuloServiceImpl}")
	private BsModuloService bsModuloServiceImpl;
	
	@ManagedProperty("#{reportesServiceClientImpl}")
	private ReportesServiceClient reportesServiceClientImpl;

	@PostConstruct
	public void init() {
		this.cleanFields();

	}

	public void cleanFields() {
		this.bsModulo = null;
		this.bsModuloSelected = null;
		this.lazyModel = null;
		this.esNuegoRegistro = true;
		this.estadoList = List.of(Estado.ACTIVO.getEstado(), Estado.INACTIVO.getEstado());
	}

	// GETTERS & SETTERS
	public BsModulo getBsModulo() {
		if (Objects.isNull(bsModulo)) {
			this.bsModulo = new BsModulo();
		}
		return bsModulo;
	}

	public void setBsModulo(BsModulo bsModulo) {
		this.bsModulo = bsModulo;
	}

	public BsModulo getBsModuloSelected() {
		if (Objects.isNull(bsModuloSelected)) {
			this.bsModuloSelected = new BsModulo();

		}
		return bsModuloSelected;
	}

	public void setBsModuloSelected(BsModulo bsModuloSelected) {
		if (!Objects.isNull(bsModuloSelected)) {
			this.bsModulo = bsModuloSelected;
			this.esNuegoRegistro = false;
		}
		this.bsModuloSelected = bsModuloSelected;
	}

	public List<String> getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List<String> estadoList) {
		this.estadoList = estadoList;
	}

	public LazyDataModel<BsModulo> getLazyModel() {
		if (Objects.isNull(lazyModel)) {
			lazyModel = new GenericLazyDataModel<BsModulo>(bsModuloServiceImpl.buscarTodosLista());
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<BsModulo> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public BsModuloService getBsModuloServiceImpl() {
		return bsModuloServiceImpl;
	}

	public void setBsModuloServiceImpl(BsModuloService bsModuloServiceImpl) {
		this.bsModuloServiceImpl = bsModuloServiceImpl;
	}

	public boolean isEsNuegoRegistro() {
		return esNuegoRegistro;
	}

	public void setEsNuegoRegistro(boolean esNuegoRegistro) {
		this.esNuegoRegistro = esNuegoRegistro;
	}

	public ReportesServiceClient getReportesServiceClientImpl() {
		return reportesServiceClientImpl;
	}

	public void setReportesServiceClientImpl(ReportesServiceClient reportesServiceClientImpl) {
		this.reportesServiceClientImpl = reportesServiceClientImpl;
	}

	// METODOS
	public void guardar() {
		try {
			if (!Objects.isNull(bsModuloServiceImpl.guardar(this.bsModulo))) {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se guardo correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo insertar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().executeScript("PF('manageModuloDialog').hide()");
			PrimeFaces.current().ajax().update("form:messages", "form:dt-modulo");
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			// e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

	public void delete() {
		try {
			if (!Objects.isNull(this.bsModuloSelected)) {
				this.bsModuloServiceImpl.eliminar(this.bsModuloSelected.getId());
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!",
						"El registro se elimino correctamente.");
			} else {
				CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!", "No se pudo eliminar el registro.");
			}
			this.cleanFields();
			PrimeFaces.current().ajax().update("form:messages", "form:dt-modulo");
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al Guardar", System.err);
			// e.printStackTrace(System.err);
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡ERROR!",
					e.getCause().getMessage().substring(0, 50) + "...");
		}

	}

	public void descargar() throws IOException {
	    ParametrosReporte params = new ParametrosReporte();
	    params.setCodModulo("BASE");
	    params.setFormato("PDF");
	    params.setReporte("StoArticulos");
	    params.setParametros(new ArrayList<String>());
	    params.setValor(new ArrayList<Object>());

	    // Llama al servicio y obtén la respuesta
	    Response response = this.reportesServiceClientImpl.generarReporte(params);

	    // Verifica si la respuesta es exitosa (código 200)
	    if (response.getStatus() == 200) {
	        // Configura la respuesta del servicio para la descarga
	        configureResponseForDownload(response);

	        // Obtiene el contexto externo de JSF
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

	        // Obtiene el flujo de entrada del cuerpo de la respuesta
	        InputStream inputStream = (InputStream) response.getEntity();
	        
	     // Obtener el valor del encabezado Content-Type
	        String contentType = response.getHeaderString("Content-Type");
	        System.out.println("Content-Type: " + contentType);

	        // Obtener el valor del encabezado Content-Disposition
	        String contentDisposition = response.getHeaderString("Content-Disposition");
	        System.out.println("Content-Disposition: " + contentDisposition);

	        // Extraer el valor del filename desde Content-Disposition
	        String filename = null;
	        if (contentDisposition != null && contentDisposition.toLowerCase().contains("filename")) {
	            String[] dispositionParts = contentDisposition.split(";");
	            for (String part : dispositionParts) {
	                if (part.trim().startsWith("filename")) {
	                    String[] filenameParts = part.split("=");
	                    if (filenameParts.length == 2) {
	                        filename = filenameParts[1].trim().replace("\"", "");
	                        System.out.println("Filename: " + filename);
	                    }
	                }
	            }
	        }

	        // Configura las cabeceras de la respuesta JSF para la descarga
	        externalContext.responseReset();
	        externalContext.setResponseContentType(contentType);
	        externalContext.setResponseContentLength(response.getLength());
	        externalContext.setResponseHeader("Content-Disposition", contentDisposition);

	        // Obtén el flujo de salida de la respuesta
	        OutputStream outputStream = externalContext.getResponseOutputStream();

	        // Copia los bytes del flujo de entrada al flujo de salida
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = inputStream.read(buffer)) > 0) {
	            outputStream.write(buffer, 0, length);
	        }

	        // Cierra los flujos
	        inputStream.close();
	        outputStream.flush();
	        outputStream.close();

	        // Finaliza la respuesta JSF
	        FacesContext.getCurrentInstance().responseComplete();
	    } else {
	        // Maneja el caso en que la respuesta no fue exitosa
	        System.out.println("Error en la solicitud: " + response.getStatus());
	        // Puedes manejar el error de alguna manera en tu aplicación
	    }
	}


    private void configureResponseForDownload(Response response) {
        // Configura la respuesta del servicio para la descarga
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse servletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        // Obtiene los encabezados de la respuesta del servicio
        MultivaluedMap<String, Object> headers = response.getHeaders();
        
        // Configura los encabezados de la respuesta del servicio en la respuesta del servlet
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            List<Object> headerValues = entry.getValue();
            for (Object headerValue : headerValues) {
                servletResponse.addHeader(headerName, headerValue.toString());
            }
        }
        
        // Establece el código de estado de la respuesta del servicio en la respuesta del servlet
        servletResponse.setStatus(response.getStatus());
    }
}
