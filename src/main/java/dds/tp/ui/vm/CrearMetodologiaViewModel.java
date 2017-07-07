package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Condicion;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

@Observable
public class CrearMetodologiaViewModel {

	private String nombreMetodologia = "";
	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	private RepositorioMetodologias repoMetodologias;
	private Condicion condicion;
	
	public CrearMetodologiaViewModel(RepositorioIndicadores repoIndicadores, RepositorioMetodologias repoMetodologias) {
		super();
		this.repoIndicadores = repoIndicadores;
		this.metodologiaBuilder = new MetodologiaBuilder();
		this.repoMetodologias = repoMetodologias;
	}

	public String getNombreMetodologia() {
		return nombreMetodologia;
	}
	
	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}
	
	public List<Condicion> getCondiciones() {
		return metodologiaBuilder.getAllCondiciones();
	}
	
	public String getNombre() {
		return condicion.getNombre();
	}
	
	public String getDescripcion() {
		return condicion.getDescripcion();
	}
	
	public Condicion getCondicion() {
		return condicion;
	}
	
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	public MetodologiaBuilder getMetodologiaBuilder() {
		return this.metodologiaBuilder;
	}

	public RepositorioIndicadores getRepoIndicadores() {
		return this.repoIndicadores;
	}

	public void guardarMetodologia() {
		this.repoMetodologias.addMetodologia(metodologiaBuilder.build());
	}
	
}
