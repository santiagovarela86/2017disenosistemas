package dds.tp.excepciones;

@SuppressWarnings("serial")
public class SintaxisIncorrecta extends RuntimeException {
	
	public SintaxisIncorrecta(String mensaje) {
		super(mensaje);
	}
}
