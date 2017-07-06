package dds.tp.model;

import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class CondicionTaxativa extends Condicion {

	public CondicionTaxativa(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	
	public abstract boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores);

}
