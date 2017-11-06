package dds.tp.model.condiciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="Indicadores")
public abstract class Comparable {
	
	@Id
	@GeneratedValue
	private Long id ;
	
	public abstract Double evaluar(Empresa empresa,Balance balance, RepositorioIndicadores baulIndicadores);
	public abstract boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores);
}
