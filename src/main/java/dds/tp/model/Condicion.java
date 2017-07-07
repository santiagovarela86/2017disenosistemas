package dds.tp.model;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public abstract class Condicion {

	private String nombre;
	private String descripcion;
	
	public Condicion(String nombre, String descripcion){
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
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
}
