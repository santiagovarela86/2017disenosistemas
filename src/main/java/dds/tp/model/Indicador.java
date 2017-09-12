package dds.tp.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;

import dds.tp.calculador.Expresion;
import dds.tp.jpa.converters.ExpresionConverter;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.Parser;

@Entity
@Observable
public class Indicador extends Comparado {
	
	private String nombre;
	@Column
	@Convert(converter=ExpresionConverter.class)
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

	@Override
	public Double evaluar(Empresa empresa, Balance balance, RepositorioIndicadores baulIndicadores) {
		return this.evaluar(balance, baulIndicadores);
	}
}
