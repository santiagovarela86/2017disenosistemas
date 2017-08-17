package dds.tp.model.criterios;

import java.time.Year;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class Criterio {

	public abstract boolean evaluar(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores);

	public void evaluarRequisitosEn(Condicion condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		Anual periodoAEvaluar = new Anual(Year.now().getValue());
		for (int i = 1; i <= condicion.getCantDePeriodosHaciaAtras(); i++) {
			condicion.getIndicador().evaluar(empresa.getBalance(periodoAEvaluar),repoIndicadores);
			periodoAEvaluar = periodoAEvaluar.anioAnterior();
		}
	}
	
}
