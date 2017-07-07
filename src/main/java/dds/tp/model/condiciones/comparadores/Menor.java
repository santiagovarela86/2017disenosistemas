package dds.tp.model.condiciones.comparadores;

public class Menor implements Comparador {

	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno < valorDos;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return "Menor";
	}
	@Override
	public String toString() {
		return this.getNombre();
	}

}
