package dds.tp.excepciones;

@SuppressWarnings("serial")
public class ElementoYaExiste extends RuntimeException {

	public ElementoYaExiste(String mensaje) {
		super(mensaje);
	}
}
