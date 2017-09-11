package dds.tp.model.condiciones.comparadores;

import javax.persistence.Entity;

@Entity
public class MayorIgual extends Comparador {
	
	@Override
	public boolean comparar(Double valorUno, Double valorDos) {
		return valorUno >= valorDos;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return "Mayor o Igual";
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}

}
