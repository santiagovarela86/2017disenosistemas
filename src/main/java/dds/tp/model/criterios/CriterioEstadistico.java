package dds.tp.model.criterios;

import javax.persistence.Convert;
import javax.persistence.Entity;

import dds.tp.jpa.converters.ModoEstadisticoConverter;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public class CriterioEstadistico extends Criterio {
	
	@Convert(converter=ModoEstadisticoConverter.class)
	private ModoEstadistico modo;
	
	public CriterioEstadistico() {
		// TODO Auto-generated constructor stub
	}
	
	public CriterioEstadistico(ModoEstadistico modo) {
		this.modo = modo;
	}
	
	public boolean cumpleCriterio(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return condicion.getComparador()
				.comparar(modo.getEstadistica(empresa, condicion.getIndicador(), repoIndicadores),condicion.getValorLimite());
	}

}
