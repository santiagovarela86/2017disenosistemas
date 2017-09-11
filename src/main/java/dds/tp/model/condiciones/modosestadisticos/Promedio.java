package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Promedio extends ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Comparado indicador, RepositorioIndicadores repoIndicadores) {
		
		return empresa.getBalancesAnuales().stream()
										   .mapToDouble(b -> indicador.evaluar(empresa, b, repoIndicadores))
										   .average().getAsDouble(); 
	}
	
}
