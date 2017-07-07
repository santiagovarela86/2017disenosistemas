package dds.tp.ui.vm.condiciones;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.CondicionComparadora;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.repositorios.RepositorioComparadores;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class CargarCondicionComparadoraViewModel {
	
	private String nombreCondicion = "";
	private String descripcion = "";
	private String nombreIndicador = "";
	private String periodosHaciaAtras;
	private RepositorioComparadores repoComparadores;
	private Comparador comparadorSeleccionado;
	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	
	public CargarCondicionComparadoraViewModel(MetodologiaBuilder metodologiaBuilder,
			RepositorioIndicadores repoIndicadores) {
		super();
		this.metodologiaBuilder = metodologiaBuilder;
		this.repoIndicadores = repoIndicadores;
		this.repoComparadores = new RepositorioComparadores();
	}

	public String getNombreCondicion() {
		return nombreCondicion;
	}
	
	public void setNombreCondicion(String nombreCondicion) {
		this.nombreCondicion = nombreCondicion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public String getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(String periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
	
	public void guardarCondicionComparadora() {
		metodologiaBuilder.agregarCondPriorizar(
					new CondicionComparadora(this.nombreCondicion, 
							this.descripcion, repoIndicadores.getIndicador(nombreIndicador), 
							this.comparadorSeleccionado, Integer.parseInt(this.periodosHaciaAtras)));
		
	}
	
	public List<Comparador> getComparadores() {
		return this.repoComparadores.getComparadores();
	}

	public void setComparadorSeleccionado(Comparador comparadorSeleccionado) {
		this.comparadorSeleccionado = comparadorSeleccionado;
	}
	
	public Comparador getComparadorSeleccionado() {
		return comparadorSeleccionado;
	}
}
