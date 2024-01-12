package py.com.capitalsys.capitalsysservices.services.impl.cobranzas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import py.com.capitalsys.capitalsysdata.dao.cobranzas.CobSaldoRepository;
import py.com.capitalsys.capitalsysentities.entities.cobranzas.CobSaldo;
import py.com.capitalsys.capitalsysservices.services.cobranzas.CobSaldoService;
import py.com.capitalsys.capitalsysservices.services.impl.CommonServiceImpl;

/*
* 12 ene. 2024 - Elitebook
*/
@Service
public class CobSaldoServiceImpl extends CommonServiceImpl<CobSaldo, CobSaldoRepository> implements CobSaldoService {

	@Override
	public BigDecimal calcularTotalSaldoAFecha(Long idEmpresa, Long idCliente) {
		return this.repository.calcularTotalSaldoAFecha(idEmpresa, idCliente);
	}

	@Override
	public List<CobSaldo> buscarCobSaldoActivosLista(Long idEmpresa) {
		return this.repository.buscarCobSaldoActivosLista(idEmpresa);
	}

	@Override
	public List<CobSaldo> buscarSaldoPorClienteLista(Long idEmpresa, Long idCliente) {
		return this.repository.buscarSaldoPorClienteLista(idEmpresa, idCliente);
	}

	@Override
	public List<CobSaldo> buscarSaldoPorClienteMayorACeroLista(Long idEmpresa, Long idCliente) {
		return this.repository.buscarSaldoPorClienteMayorACeroLista(idEmpresa, idCliente);
	}

}
