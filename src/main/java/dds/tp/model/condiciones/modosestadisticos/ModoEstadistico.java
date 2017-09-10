package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class  ModoEstadistico {
	public abstract Double getEstadistica(Empresa empresa, Comparado nombreAPensar, RepositorioIndicadores repoIndicadores);
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
