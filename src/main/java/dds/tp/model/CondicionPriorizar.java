package dds.tp.model;

import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class CondicionPriorizar extends Condicion {

	public CondicionPriorizar(String nombre, String descripcion) {
		super(nombre, descripcion);
	}

	public abstract boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores);
}
