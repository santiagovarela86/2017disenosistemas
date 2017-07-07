package dds.tp.model.condiciones.comparadores;

public interface Comparador {
	public String getNombre();
	public boolean comparar(Double valorUno, Double valorDos);
}
