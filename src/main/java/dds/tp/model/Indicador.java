package dds.tp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;

import dds.tp.calculador.Expresion;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.condiciones.Comparable;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuarios;
import dds.tp.parsertools.Parser;

@Entity
@Observable
public class Indicador extends Comparable {
	
	private String nombre;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Expresion expresion;
	
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	public Indicador() {
		// TODO Auto-generated constructor stub
	}

	public Indicador(String nombre, Expresion exp, Usuario usuario){
		this.nombre = nombre;
		this.expresion = exp;
		this.setUsuario(usuario);
	}

	public Indicador(String nombre, String exp, Usuario usuario) throws SintaxisIncorrecta {
		this.nombre = nombre;
		this.expresion = new Expresion(new Parser().parsear(exp));
		this.setUsuario(usuario);
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public Double evaluar(Balance balance, RepositorioIndicadores baulIndicadores) {	
		return expresion.calculateCon(balance, baulIndicadores, this.getUsuario());
	}
	
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores) {
		//Ak habria q fijarse si tiene todas las cuentas la empresa y las cuentas q usan los subindicadores
		//Por ahora lo resuelvo con un try catch
		try {
			expresion.calculateCon(balance, baulIndicadores, this.getUsuario());
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
