package dds.tp.model;

import org.uqbar.commons.utils.Observable;

import dds.tp.calculador.Expresion;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.Parser;


@Observable
public class Indicador {
	
	private String nombre;
	private Expresion expresion;
	
	public Indicador(String nombre, Expresion exp){
		this.nombre = nombre;
		this.expresion = exp;
	}
	
	public Indicador(String nombre, String exp){
		this.nombre = nombre;
		this.expresion = new Expresion(new Parser().parsear(exp));
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public Double evaluar(Balance balance, RepositorioIndicadores baulIndicadores) {	
		return expresion.calculateCon(balance, baulIndicadores);
	}
	
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores) {
		//Ak habria q fijarse si tiene todas las cuentas la empresa y las cuentas q usan los subindicadores
		//Por ahora lo resuelvo con un try catch
		try {
			expresion.calculateCon(balance, baulIndicadores);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getFormula(){
		return this.expresion.toString();
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}
}
