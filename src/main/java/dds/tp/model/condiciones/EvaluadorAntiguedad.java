package dds.tp.model.condiciones;

import javax.persistence.Entity;
import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public class EvaluadorAntiguedad extends Comparado {
	
	@Override
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores) {
		return true;
	}

	@Override
	public Double evaluar(Empresa empresa, Balance balance, RepositorioIndicadores baulIndicadores) {
		return empresa.getAntiguedad().doubleValue();
	}

}
