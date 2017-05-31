package dds.tp.excepciones;

@SuppressWarnings("serial")
public class IndicadorNotFound extends Exception {
	public IndicadorNotFound(String nombreIndicadorNoEncontrado, String indiadorQueLoPide) {
		super("No se ha encontrado el indicador " + nombreIndicadorNoEncontrado + " que lo necesita el indicador " + indiadorQueLoPide);
	}
}
