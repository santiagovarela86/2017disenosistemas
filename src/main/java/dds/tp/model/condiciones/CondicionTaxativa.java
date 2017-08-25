package dds.tp.model.condiciones;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.criterios.Criterio;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionTaxativa extends Condicion {
	
	private Double valorLimite;
	private Criterio criterio;
	
	public CondicionTaxativa(String nombre, String descripcion, Indicador indicador, Comparador comparador,
			int periodosHaciaAtras, Criterio criterio, Double valorLimite) {		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras);
		this.criterio = criterio;
		this.valorLimite = valorLimite;
	}
	
	public Double getValorLimite() {
		return valorLimite;
	}
	
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return criterio.cumpleCriterio(this, empresa, repoIndicadores);
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
