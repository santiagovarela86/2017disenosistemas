package dds.tp.model.criterios;

import java.time.Year;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CriterioMayormente extends Criterio {

	public boolean evaluar(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores){
		Anual periodoEvaluar = new Anual(Year.now().getValue()-condicion.getCantDePeriodosHaciaAtras()+1);
		Double valorAUsar =  condicion.getIndicador().evaluar(empresa.getBalance(periodoEvaluar), repoIndicadores);
		int vecesCumplido = 0;
		for (int i = 1; i < condicion.getCantDePeriodosHaciaAtras(); i++) {
			periodoEvaluar = periodoEvaluar.anioSiguiente();
			Double valorSiguiente = condicion.getIndicador().evaluar(empresa.getBalance(periodoEvaluar), repoIndicadores);
			if(condicion.getComparador().comparar(valorAUsar, valorSiguiente))
				vecesCumplido++;
			valorAUsar = valorSiguiente;
		}
		return vecesCumplido>condicion.getCantDePeriodosHaciaAtras()*0.70;
	}

}
