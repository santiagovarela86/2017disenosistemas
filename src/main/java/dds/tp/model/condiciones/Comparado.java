package dds.tp.model.condiciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Comparado {
	
	@Id
	@GeneratedValue (strategy = GenerationType.TABLE)
	private Long id ;
	public abstract Double evaluar(Empresa empresa,Balance balance, RepositorioIndicadores baulIndicadores);
	public abstract boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores);
}
