package dds.tp.model.condiciones;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionPriorizante extends Condicion {

	public CondicionPriorizante(String nombre, String descripcion, NombreAPensar nombreAPensar, Comparador comparador,
			int periodosAEvaluar) {
		super(nombre, descripcion, nombreAPensar, comparador, periodosAEvaluar);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		return this.getPeriodosAEvaluar().stream().allMatch(periodo -> emp1EsMejorQueEmp2(empresa1, empresa2, repoIndicadores, (Anual) periodo));
	}
	
	private boolean emp1EsMejorQueEmp2(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores, Anual anio) {
		return this.comparador
				.comparar(obtenerResultado(empresa1, anio, repoIndicadores), obtenerResultado(empresa2, anio, repoIndicadores));
	}
	
	private Double obtenerResultado(Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores){
		return this.valorAComparar.evaluar(empresa, empresa.getBalance(anio), repoIndicadores);
	}
	
}
