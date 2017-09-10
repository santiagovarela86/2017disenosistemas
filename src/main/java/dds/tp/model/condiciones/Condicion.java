package dds.tp.model.condiciones;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.BalanceAnual;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public abstract class Condicion {

	protected String nombre;
	protected String descripcion;
	protected Comparado indicador;
	protected Comparador comparador;
	protected int cantidadDePeriodosAEvaluar;

	public Condicion(String nombre, String descripcion, Comparado indicador, Comparador comparador, int cantidadDePeriodosAEvaluar){
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.indicador = indicador;
		this.comparador = comparador;
		this.cantidadDePeriodosAEvaluar = cantidadDePeriodosAEvaluar;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCantidadDePeriodosAEvaluar() {
		return cantidadDePeriodosAEvaluar;
	}
	
	public Comparado getIndicador() {
		return indicador;
	}
	
	public Comparador getComparador() {
		return comparador;
	}
	
	
	public List<Periodo> getPeriodosAEvaluar() {
		Anual periodoAIngresar = Anual.getPeriodoActual();
		ArrayList<Periodo> periodosAEvaluar = new ArrayList<>();
		for(int per = 0; per < cantidadDePeriodosAEvaluar; per++)
		{
			periodosAEvaluar.add(periodoAIngresar);
			periodoAIngresar = periodoAIngresar.anioAnterior();
		}
		return periodosAEvaluar;
	}
	
	public boolean empresaPuedeSerEvaluada(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		boolean resultado =  this.getPeriodosAEvaluar().stream()
					.allMatch(periodo -> empresa.contieneBalance(new BalanceAnual( (Anual) periodo)) 
					&& this.indicador.puedeEvaluar(empresa.getBalance(periodo), repoIndicadores));
		return resultado;
	}
	
}
