package dds.tp.excepciones;

@SuppressWarnings("serial")
public class CondicionNoCumplida extends RuntimeException {
	
	public CondicionNoCumplida(String mensaje) {
		super(mensaje);
	}
}
