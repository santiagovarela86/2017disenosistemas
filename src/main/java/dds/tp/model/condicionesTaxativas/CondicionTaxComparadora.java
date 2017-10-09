package dds.tp.model.condicionesTaxativas;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionTaxComparadora extends CondicionTaxativa {
	
	public CondicionTaxComparadora(String nombre, String descripcion, Comparado indicador, Comparador comparador,
			int periodosHaciaAtras, Double valorLimite) {		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras,valorLimite);
	}

	@Override
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return this.getPeriodosAEvaluar().stream()
				.allMatch(periodo -> this.getComparador()
						.comparar(this.indicador.evaluar(empresa, empresa.getBalance(periodo), repoIndicadores),this.valorLimite));
	}
}
