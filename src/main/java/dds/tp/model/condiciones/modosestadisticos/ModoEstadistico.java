package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public interface ModoEstadistico {
	public Double getEstadistica(Empresa empresa, Indicador indicadorAUsar, RepositorioIndicadores repoIndicadores);
}
