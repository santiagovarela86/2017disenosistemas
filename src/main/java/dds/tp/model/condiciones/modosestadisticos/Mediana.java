package dds.tp.model.condiciones.modosestadisticos;

import java.util.ArrayList;

import dds.tp.model.BalanceAnual;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparable;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Mediana extends ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Comparable indicador, RepositorioIndicadores repoIndicadores) {
		ArrayList<Double> resultados = new ArrayList<>();
		for (BalanceAnual bal : empresa.getBalancesAnuales()) {
			resultados.add(indicador.evaluar(empresa, bal, repoIndicadores));
		}
		resultados.sort((p1,p2) -> p1.compareTo(p2));
		return getResultadoBySize(resultados);
	}

	private Double getResultadoBySize(ArrayList<Double> resultados) {
		int tamanio = resultados.size();
		boolean esCantidadPar = (tamanio % 2) == 0;
		if(esCantidadPar){
			return (resultados.get(tamanio/2) + resultados.get(tamanio/2-1))/2;
		}
		else{
			return resultados.get(tamanio/2-1);
					
		}
	}
}
