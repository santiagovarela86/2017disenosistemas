package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Promedio extends ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Indicador indicadorAUsar, RepositorioIndicadores repoIndicadores) {
		
		return empresa.getBalancesAnuales().stream()
										   .mapToDouble(b -> indicadorAUsar.evaluar(b, repoIndicadores))
										   .average().getAsDouble(); 
	}
	
}
