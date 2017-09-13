package dds.tp.model.condiciones.comparadores;


public class MenorIgual extends Comparador {
	
	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno <= valorDos;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return "Menor o Igual";
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}

}
