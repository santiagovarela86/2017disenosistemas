package dds.tp.ui.vm.condiciones;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.condicionesTaxativas.CondicionTaxEstadistica;
import dds.tp.model.repositorios.RepositorioComparadores;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioModoEstadistico;

@Observable
public class CargarCondicionEstadisticaViewModel {
	private String nombreCondicion = "";
	private String descripcion = "";
	private String nombreIndicador = "";
	private String valor;
	private RepositorioComparadores repoComparadores;
	private RepositorioModoEstadistico repoModoEstadisticos;
	private Comparador comparadorSeleccionado;
	private ModoEstadistico modoSeleccionado;
	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	private String mensajeError;
	
	public CargarCondicionEstadisticaViewModel(MetodologiaBuilder metodologiaBuilder,
			RepositorioIndicadores repoIndicadores) {
		super();
		this.metodologiaBuilder = metodologiaBuilder;
		this.repoIndicadores = repoIndicadores;
		this.repoComparadores = new RepositorioComparadores();
		this.repoModoEstadisticos = new RepositorioModoEstadistico();
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public void guardarCondicionEstadistica() {
		if(nombreCondicion.isEmpty() || descripcion.isEmpty())
			throw new RuntimeException("Nombre y descripcion son obligatorios");
		metodologiaBuilder.agregarCondTaxativa(
				new CondicionTaxEstadistica(this.nombreCondicion, this.descripcion, repoIndicadores.getIndicador(nombreIndicador),
						this.comparadorSeleccionado, 0, Double.parseDouble(this.valor), this.modoSeleccionado));		
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
	
	public List<ModoEstadistico> getModosEstadisticos() {
		return this.repoModoEstadisticos.getModosEstadisticos();
	}
	
	public ModoEstadistico getModoSeleccionado() {
		return modoSeleccionado;
	}
	
	public void setModoSeleccionado(ModoEstadistico modoSeleccionado) {
		this.modoSeleccionado = modoSeleccionado;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}
	
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
