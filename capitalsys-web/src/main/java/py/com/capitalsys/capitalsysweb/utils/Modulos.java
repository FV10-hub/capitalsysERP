package py.com.capitalsys.capitalsysweb.utils;

/*
* 15 dic. 2023 - Elitebook
*/
public enum Modulos {
	BASE("BS"),
	COBRANZAS("COB"),
	CREDITOS("CRE"),
	TESORERIA("TES"),
	VENTAS("VEN"),
	CONTABILIDAD("CON"),
	STOCK("STO");
    

    private final String modulo;

	private Modulos(String modulo) {
		this.modulo = modulo;
	}

	public String getModulo() {
		return modulo;
	}
}
