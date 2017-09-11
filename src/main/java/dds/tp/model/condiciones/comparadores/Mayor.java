package dds.tp.model.condiciones.comparadores;

import javax.persistence.Entity;

@Entity
public class Mayor extends Comparador {
	
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
