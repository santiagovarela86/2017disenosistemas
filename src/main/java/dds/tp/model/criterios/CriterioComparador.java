package dds.tp.model.criterios;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CriterioComparador extends Criterio {

	public boolean cumpleCriterio(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return condicion.getPeriodosAEvaluar().stream()
			.allMatch(periodo -> condicion.getComparador()
					.comparar(condicion.getIndicador().evaluar(empresa.getBalance(periodo), repoIndicadores),condicion.getValorLimite()));
	}
}
