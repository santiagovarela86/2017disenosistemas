package dds.tp.model.condiciones;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Periodo;

@Observable
public abstract class Condicion {

	protected String nombre;
	protected String descripcion;
	protected Indicador indicador;
	protected Comparador comparador;
	protected int cantidadDePeriodosAEvaluar;
	
	public abstract List<Periodo> getPeriodosAEvaluar();
	
	public Condicion(String nombre, String descripcion, Indicador indicador, Comparador comparador, int cantidadDePeriodosAEvaluar){
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
	
	public Indicador getIndicador() {
		return indicador;
	}
	
	public Comparador getComparador() {
		return comparador;
	}
}
