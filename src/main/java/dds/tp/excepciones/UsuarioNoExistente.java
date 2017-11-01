package dds.tp.excepciones;

@SuppressWarnings("serial")
public class UsuarioNoExistente extends RuntimeException {
	
	public UsuarioNoExistente(String mensaje) {
		super(mensaje);
	}

}
