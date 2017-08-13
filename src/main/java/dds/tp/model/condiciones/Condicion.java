package dds.tp.model.condiciones;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public abstract class Condicion {

	private String nombre;
	private String descripcion;
	private Indicador indicador;
	private Comparador comparador;
	private int periodosHaciaAtras;
	
	public Condicion(String nombre, String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras){
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setIndicador(indicador);
		this.setComparador(comparador);
		this.setPeriodosHaciaAtras(periodosHaciaAtras);
	}
	
	public abstract void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores);
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public Comparador getComparador() {
		return comparador;
	}

	public void setComparador(Comparador comparador) {
		this.comparador = comparador;
	}

	public int getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(int periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
}
