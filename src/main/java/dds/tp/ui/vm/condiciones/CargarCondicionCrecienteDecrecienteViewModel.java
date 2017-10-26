package dds.tp.ui.vm.condiciones;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.condicionesTaxativas.CondicionTaxPendiente;
import dds.tp.model.repositorios.RepositorioComparadores;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class CargarCondicionCrecienteDecrecienteViewModel {
	private String nombreCondicion = "";
	private String descripcion = "";
	private String nombreIndicador = "";
	private String periodosHaciaAtras;
	private RepositorioComparadores repoComparadores;
	private Comparador comparadorSeleccionado;
	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	private String mensajeError;
	
	
	
	public CargarCondicionCrecienteDecrecienteViewModel(MetodologiaBuilder metodologiaBuilder,
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
	
	public void guardarCondicionCrecienteDecreciente() {
		if(nombreCondicion.isEmpty() || descripcion.isEmpty())
			throw new RuntimeException("Nombre y descripcion son obligatorios");
		metodologiaBuilder.agregarCondTaxativa(
				new CondicionTaxPendiente(this.nombreCondicion, this.descripcion, repoIndicadores.getIndicador(nombreIndicador), 
					this.comparadorSeleccionado, Integer.parseInt(this.periodosHaciaAtras), null));
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
	
	public String getMensajeError() {
		return mensajeError;
	}
	
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

}
