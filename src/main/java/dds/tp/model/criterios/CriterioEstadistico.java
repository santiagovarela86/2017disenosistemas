package dds.tp.model.criterios;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public class CriterioEstadistico extends Criterio {
	
	@OneToOne
	private ModoEstadistico modo;
	
	public CriterioEstadistico(ModoEstadistico modo) {
		this.modo = modo;
	}
	
	public boolean cumpleCriterio(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return condicion.getComparador()
				.comparar(modo.getEstadistica(empresa, condicion.getIndicador(), repoIndicadores),condicion.getValorLimite());
	}

}
