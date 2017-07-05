package dds.tp.model.condiciones.comparadores;

public class MayorIgual implements Comparador {

	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno >= valorDos;
	}

}
