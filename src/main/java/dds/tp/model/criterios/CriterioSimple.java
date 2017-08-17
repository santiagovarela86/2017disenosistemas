package dds.tp.model.criterios;

import java.time.Year;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CriterioSimple extends Criterio {

	public boolean evaluar(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores){
		Anual periodoAEvaluar = new Anual(Year.now().getValue());
		boolean seCumple = true;
		for (int i = 1; i <= condicion.getCantDePeriodosHaciaAtras(); i++) {
			seCumple = seCumple && comparar(condicion.getIndicador(), condicion.getComparador(), empresa, periodoAEvaluar, repoIndicadores, condicion.getValorLimite());
  			periodoAEvaluar = periodoAEvaluar.anioAnterior();
		}

		return seCumple;
	}

	private boolean comparar(Indicador indicador, Comparador comparador, Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores, Double valorLimite) {
		return comparador.comparar(indicador.evaluar(empresa.getBalance(anio), repoIndicadores),valorLimite);
	}

}
