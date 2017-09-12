package dds.tp.model.condiciones.comparadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public abstract class Comparador{
	
	public abstract String getNombre();
	public abstract boolean comparar(Double valorUno, Double valorDos);
}
