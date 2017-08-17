package dds.tp.model.condiciones;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public abstract class Condicion {

	private String nombre;
	private String descripcion;
	private Indicador indicador;
	private Comparador comparador;
	private int cantDePeriodosHaciaAtras;
	
	public Condicion(String nombre, String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras){
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setIndicador(indicador);
		this.setComparador(comparador);
		this.setCantDePeriodosHaciaAtras(periodosHaciaAtras);
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

	public int getCantDePeriodosHaciaAtras() {
		return cantDePeriodosHaciaAtras;
	}

	public void setCantDePeriodosHaciaAtras(int cantDePeriodosHaciaAtras) {
		this.cantDePeriodosHaciaAtras = cantDePeriodosHaciaAtras;
	}
	
	public List<Periodo> getPeriodosAEvaluar() {
		ArrayList<Periodo> periodosAEvaluar = new ArrayList<>();
		Anual periodoAEvaluarTemp = new Anual(Year.now().getValue());
		periodosAEvaluar.add(periodoAEvaluarTemp);
		for(int i = 0; i < cantDePeriodosHaciaAtras ; i++) {
			periodosAEvaluar.add(periodoAEvaluarTemp.anioAnterior());
		}
		return periodosAEvaluar;
	}
}
