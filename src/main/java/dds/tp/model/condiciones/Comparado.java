package dds.tp.model.condiciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public abstract class Comparado {
	
	@Id
	@GeneratedValue
	private Long id;
	
	public abstract Double evaluar(Empresa empresa,Balance balance, RepositorioIndicadores baulIndicadores);
	public abstract boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores);
}
