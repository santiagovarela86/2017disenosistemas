package dds.tp.model.condiciones.comparadores;

import javax.persistence.Entity;

@Entity
public class Menor extends Comparador {
	
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
