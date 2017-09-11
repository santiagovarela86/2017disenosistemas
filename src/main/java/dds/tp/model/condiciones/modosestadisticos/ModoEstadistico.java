package dds.tp.model.condiciones.modosestadisticos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public abstract class  ModoEstadistico {
	
	@Id
	@GeneratedValue
	private Long id;
	
	public abstract Double getEstadistica(Empresa empresa, Comparado indicador, RepositorioIndicadores repoIndicadores);
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
