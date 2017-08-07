package dds.tp.model.condiciones;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;
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

	public  boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return false;
	}
	
	public  boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores) {
		return false;
	}
	
	public int evaluarInt(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repositorioIndicadores){
		if (this.evaluar(empresa1, empresa2, repositorioIndicadores)) 
			return -1;
		else 
			return 1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
