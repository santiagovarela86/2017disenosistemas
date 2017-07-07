package dds.tp.model.condiciones.modosestadisticos;

import java.util.ArrayList;

import dds.tp.model.BalanceAnual;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Media implements ModoEstadistico {

	@Override
	public Double getEstadistica(Empresa empresa, Indicador indicadorAUsar, RepositorioIndicadores repoIndicadores) {
		ArrayList<Double> resultados = new ArrayList<>();
		for (BalanceAnual bal : empresa.getBalancesAnuales()) {
			resultados.add(indicadorAUsar.evaluar(bal, repoIndicadores));
		}
		resultados.sort((p1,p2) -> p1.compareTo(p2));
		if(resultados.size()%2!=0){
			return resultados.get(resultados.size()/2-1);
		}
		else{
			return (resultados.get(resultados.size()/2)+resultados.get(resultados.size()/2-1))/2;
		}
	}
	
	@Override
	public String toString() {
		return "Media";
	}

}
