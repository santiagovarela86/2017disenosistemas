package dds.tp.model;

import org.uqbar.commons.utils.Observable;

@Observable
public class Cuenta {

	private String nombre;
	private Float valor;
	
	public Cuenta(String nombre, Float valor) {
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

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String toString(){
		return this.nombre + ": " + this.valor;
	}
	
}
