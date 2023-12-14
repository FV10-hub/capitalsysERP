package py.com.capitalsys.capitalsysservices.services.client;

import javax.ws.rs.core.Response;

import py.com.capitalsys.capitalsysentities.dto.ParametrosReporte;

/*
* 14 dic. 2023 - Elitebook
*/
public interface ReportesServiceClient {
	Response generarReporte(ParametrosReporte params);
}
