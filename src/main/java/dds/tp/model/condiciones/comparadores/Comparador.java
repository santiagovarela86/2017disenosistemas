package dds.tp.model.condiciones.comparadores;
import dds.tp.excepciones.SintaxisIncorrecta;


public abstract class Comparador{
	
	public abstract String getNombre();
	public abstract boolean comparar(Double valorUno, Double valorDos);
	
	public static Comparador crearComparador(String nombre) {
		switch (nombre) {
		case "Mayor":
			return new Mayor();
		case "Menor":
			return new Menor();
		case "Menor o Igual":
			return new MenorIgual();
		case "Mayor o Igual":
			return new MayorIgual();
		default:
			throw new SintaxisIncorrecta("No existe comparador con este nombre: " + nombre);
		}
	}
}
