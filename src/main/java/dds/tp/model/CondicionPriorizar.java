package dds.tp.model;

import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class CondicionPriorizar extends Condicion {

	public CondicionPriorizar(String nombre, String descripcion) {
		super(nombre, descripcion);
	}

	public abstract boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores);
	
	public int evaluarInt(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repositorioIndicadores){
		if (this.evaluar(empresa1, empresa2, repositorioIndicadores)){
			return 1;
		} else return -1;
	}
}
