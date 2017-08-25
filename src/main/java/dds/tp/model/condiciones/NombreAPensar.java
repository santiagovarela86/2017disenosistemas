package dds.tp.model.condiciones;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public interface NombreAPensar {
	public Double evaluar(Empresa empresa,Balance balance, RepositorioIndicadores baulIndicadores);
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores);
}
