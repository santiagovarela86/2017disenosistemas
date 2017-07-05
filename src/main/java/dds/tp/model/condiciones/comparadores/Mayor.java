package dds.tp.model.condiciones.comparadores;

public class Mayor implements Comparador {

	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno > valorDos;
	}

}
