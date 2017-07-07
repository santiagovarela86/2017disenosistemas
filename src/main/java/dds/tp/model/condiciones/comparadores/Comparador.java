package dds.tp.model.condiciones.comparadores;

import org.uqbar.commons.utils.Observable;

public interface Comparador {
	public String getNombre();
	public boolean comparar(Double valorUno, Double valorDos);
}
