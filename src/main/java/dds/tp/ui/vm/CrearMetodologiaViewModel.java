package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Condicion;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class CrearMetodologiaViewModel {

	private String nombreMetodologia = "";
	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	private Condicion condicion;
	
	public CrearMetodologiaViewModel(RepositorioIndicadores repoIndicadores) {
		super();
		this.repoIndicadores = repoIndicadores;
		this.metodologiaBuilder = new MetodologiaBuilder();
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
		// TODO Auto-generated method stub
		return this.metodologiaBuilder;
	}

	public RepositorioIndicadores getRepoIndicadores() {
		// TODO Auto-generated method stub
		return this.repoIndicadores;
	}
	
}
