package dds.tp.model;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador {
	
	private String nombre;
	private String formula;
	
	Indicador(String nombre, String formula){
		this.nombre = nombre;
		this.formula = formula;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void setFormula(String formula){
		this.formula = formula;
	}
	
	public String getFormulae(){
		return this.formula;
	}
}
