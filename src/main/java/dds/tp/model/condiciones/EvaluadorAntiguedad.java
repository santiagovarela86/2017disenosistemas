package dds.tp.model.condiciones;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class EvaluadorAntiguedad implements Comparado {

	@Override
	public boolean puedeEvaluar(Balance balance, RepositorioIndicadores baulIndicadores) {
		return true;
	}

	@Override
	public Double evaluar(Empresa empresa, Balance balance, RepositorioIndicadores baulIndicadores) {
		return empresa.getAntiguedad().doubleValue();
	}

}
