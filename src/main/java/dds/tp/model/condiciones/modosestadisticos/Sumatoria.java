package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.model.BalanceAnual;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Sumatoria implements ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Indicador indicadorAUsar, RepositorioIndicadores repoIndicadores) {
		Double sumatoria = 0d;
		for (BalanceAnual bal : empresa.getBalancesAnuales()) {
			sumatoria += indicadorAUsar.evaluar(bal, repoIndicadores);
		}
		return sumatoria;
	}

}
