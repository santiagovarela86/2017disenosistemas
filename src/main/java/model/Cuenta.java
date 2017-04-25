package model;

import org.uqbar.commons.utils.Observable;

@Observable
public class Cuenta {

	private String nombre;
	private String empresa;
	private Integer anio;
	private Float valor;

	public Cuenta(String _nombre, String _empresa, Integer _anio, Float _valor) {
		this.nombre = _nombre;
		this.empresa = _empresa;
		this.anio = _anio;
		this.valor = _valor;
	}
	
}
