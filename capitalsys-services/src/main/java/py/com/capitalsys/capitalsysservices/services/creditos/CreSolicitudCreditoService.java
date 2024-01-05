package py.com.capitalsys.capitalsysservices.services.creditos;

import java.util.List;

import py.com.capitalsys.capitalsysentities.entities.creditos.CreSolicitudCredito;
import py.com.capitalsys.capitalsysservices.services.CommonService;

/*
* 26 dic. 2023 - Elitebook
*/
public interface CreSolicitudCreditoService  extends CommonService<CreSolicitudCredito>{
	List<CreSolicitudCredito> buscarSolicitudActivosLista(Long idEmpresa);
	List<CreSolicitudCredito> buscarSolicitudAutorizadosLista(Long idEmpresa);
}
