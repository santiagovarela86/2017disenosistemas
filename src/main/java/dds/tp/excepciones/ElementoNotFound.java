package dds.tp.excepciones;

@SuppressWarnings("serial")
public class ElementoNotFound extends RuntimeException {
	
	public ElementoNotFound(String mensaje) {
		super(mensaje);
	}

}
