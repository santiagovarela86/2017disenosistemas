package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class  ModoEstadistico {
	public abstract Double getEstadistica(Empresa empresa, Indicador indicadorAUsar, RepositorioIndicadores repoIndicadores);
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
