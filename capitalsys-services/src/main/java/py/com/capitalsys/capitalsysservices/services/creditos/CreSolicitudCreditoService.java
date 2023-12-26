package py.com.capitalsys.capitalsysservices.services.creditos;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreSolicitudCreditoService {
	List<CreSolicitudCredito> buscarCobradorActivosLista(Long idEmpresa);
}
