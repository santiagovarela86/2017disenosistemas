package dds.tp.excepciones;

@SuppressWarnings("serial")
public class CuentaNotFound extends Exception{
	public CuentaNotFound(String nombreCuenta, String nombreIndicador) {
		super("No se ha encontrado la cuenta " + nombreCuenta + " que la necesita el indicador " + nombreIndicador);
	}
}
