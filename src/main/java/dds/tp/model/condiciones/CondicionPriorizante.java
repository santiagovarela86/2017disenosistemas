package dds.tp.model.condiciones;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionPriorizante extends Condicion {

	public CondicionPriorizante(String nombre, String descripcion, Indicador indicador, Comparador comparador,
			int periodosAEvaluar) {
		super(nombre, descripcion, indicador, comparador, periodosAEvaluar);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		return this.getPeriodosAEvaluar().stream().allMatch(periodo -> emp1EsMejorQueEmp2(empresa1, empresa2, repoIndicadores, (Anual) periodo));
	}
	
	private boolean emp1EsMejorQueEmp2(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores, Anual anio) {
		return this.comparador
				.comparar(obtenerResultado(empresa1, anio, repoIndicadores), obtenerResultado(empresa2, anio, repoIndicadores));
	}
	
	private Double obtenerResultado(Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores){
		return this.indicador.evaluar(empresa.getBalance(anio), repoIndicadores);
	}

	@Override
	public List<Periodo> getPeriodosAEvaluar() {
		Anual periodoAIngresar = Anual.getPeriodoActual();
		ArrayList<Periodo> periodosAEvaluar = new ArrayList<>();
		for(int per = 0; per < cantidadDePeriodosAEvaluar; per++)
		{
			periodosAEvaluar.add(periodoAIngresar);
			periodoAIngresar = periodoAIngresar.anioAnterior();
		}
		return periodosAEvaluar;
	}
	
}
