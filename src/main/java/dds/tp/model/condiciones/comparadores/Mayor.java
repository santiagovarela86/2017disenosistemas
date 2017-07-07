package dds.tp.model.condiciones.comparadores;

public class Mayor implements Comparador {

	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno > valorDos;
	}

	@Override
	public String getNombre() {
		return "Mayor";
	}

	@Override
	public String toString() {
		return this.getNombre();
	}
}
