package py.com.capitalsys.capitalsysservices.services.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.com.capitalsys.capitalsysentities.dto.ParametrosReporte;

/*
* 13 dic. 2023 - Elitebook
*/
@Service
public class ReportesServiceClientImpl implements ReportesServiceClient {

	@Value("${spring.base.url.ws.reportes}")
	String urlReportesWS;

	/**
	 * Metodo que permite consumir el webservice para generar el reporte jasper.
	 */
	@Override
	public Response generarReporte(ParametrosReporte params) {
		/*ObjectMapper obj = new ObjectMapper();
		try {
			
			System.out.println("OBJECTO JSON:::::: " + obj.writeValueAsString(params));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(urlReportesWS);
		Form form = new Form();//eso se usa si quiero enviar un form
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_OCTET_STREAM);
		Response response = invocationBuilder.post(Entity.entity(params, MediaType.APPLICATION_JSON));

		return response;
	}

	/*
	 * public static void main(String[] args) { // Ejemplo de uso
	 * ReportesJerseyClient jerseyClient = new
	 * ReportesJerseyClient("http://localhost:8080/api"); // Cambia la URL según tu
	 * configuración Map<String, Object> parameters = new HashMap<>();
	 * parameters.put("param1", "value1"); parameters.put("param2", "value2");
	 * 
	 * Response response = jerseyClient.generarReporte("reportName", parameters);
	 * 
	 * // Aquí puedes manejar la respuesta según tus necesidades if
	 * (response.getStatus() == Response.Status.OK.getStatusCode()) { // Procesar la
	 * respuesta exitosa System.out.println("Éxito: " +
	 * response.readEntity(String.class)); } else { // Manejar errores
	 * System.out.println("Error: " + response.readEntity(String.class)); }
	 * 
	 * // Importante cerrar el cliente al final jerseyClient.close(); }
	 */

}
