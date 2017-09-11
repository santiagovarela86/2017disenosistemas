package dds.tp.model.criterios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public abstract class Criterio {
	
	@Id
	@GeneratedValue
	private Long id;
	
	public abstract boolean cumpleCriterio(CondicionTaxativa condicion, Empresa empresa, RepositorioIndicadores repoIndicadores);
}
