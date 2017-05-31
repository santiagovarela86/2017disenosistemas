package dds.tp.excepciones;

@SuppressWarnings("serial")
public class ElementoYaExiste extends Exception {

	public ElementoYaExiste(String mensaje) {
		super(mensaje);
	}
}
