package dds.tp.model;

import dds.tp.model.repositorios.RepositorioIndicadores;

public interface Comparado {
	public Double evaluar(Empresa empresa,Balance balance, RepositorioIndicadores baulIndicadores);
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores);
}
