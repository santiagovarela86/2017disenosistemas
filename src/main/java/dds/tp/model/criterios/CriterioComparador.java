package dds.tp.model.criterios;

import javax.persistence.Entity;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public class CriterioComparador extends Criterio {

	public boolean cumpleCriterio(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return condicion.getPeriodosAEvaluar().stream()
			.allMatch(periodo -> condicion.getComparador()
					.comparar(condicion.getIndicador().evaluar(empresa, empresa.getBalance(periodo), repoIndicadores),condicion.getValorLimite()));
	}
}
