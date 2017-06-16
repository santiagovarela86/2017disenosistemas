package dds.tp.model;

import org.uqbar.commons.utils.Observable;

@Observable
public class Cuenta {

	private String nombre;
	private Double valor;
	
	public Cuenta(String nombre, Double valor) {
		super();
		this.nombre = nombre;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String toString(){
		return this.nombre + ": " + this.valor;
	}
	
}
