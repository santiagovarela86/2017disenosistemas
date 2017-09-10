package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Sumatoria extends ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Comparado nombreAPensar, RepositorioIndicadores repoIndicadores){
		
		return empresa.getBalancesAnuales().stream()
										   .mapToDouble(b -> nombreAPensar.evaluar(empresa, b, repoIndicadores))
										   .sum();	
		
	}
}
