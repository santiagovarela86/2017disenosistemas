package dds.tp.model.criterios;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CriterioEstadistico extends Criterio {
	
	private ModoEstadistico modo;
	
	public CriterioEstadistico(ModoEstadistico modo) {
		this.setModo(modo);
	}

	public ModoEstadistico getModo() {
		return modo;
	}

	public void setModo(ModoEstadistico modo) {
		this.modo = modo;
	}
	
	public boolean evaluar(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores){		
		Double resultadoEstadistico = this.getModo().getEstadistica(empresa, condicion.getIndicador(), repoIndicadores);
		return condicion.getComparador().comparar(resultadoEstadistico, condicion.getValorLimite());
	}

	@Override
	public void evaluarRequisitosEn(Condicion condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		modo.getEstadistica(empresa, condicion.getIndicador(), repoIndicadores);
	}

}
