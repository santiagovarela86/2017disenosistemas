package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.criterios.Criterio;
import dds.tp.model.criterios.CriterioSimple;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionPriorizante extends Condicion {
	
	private Criterio criterio = new CriterioSimple();

	public CondicionPriorizante(String nombre, String descripcion, Indicador indicador, Comparador comparador,
			int periodosHaciaAtras) {
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		
		return	this.getPeriodosAEvaluar().stream()
				.allMatch(periodo -> comparar(empresa1, empresa2, repoIndicadores, (Anual) periodo));
	}
	
	private boolean comparar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores, Anual anio) {
		return this.getComparador().comparar(obtenerResultado(empresa1, anio,repoIndicadores), 
								   obtenerResultado(empresa2, anio, repoIndicadores));
	}
	
	private Double obtenerResultado(Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores){
		Balance balance = empresa.getBalance(anio);
		return this.getIndicador().evaluar(balance, repoIndicadores);
	}
	
	public int evaluarInt(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repositorioIndicadores){
		if (this.evaluar(empresa1, empresa2, repositorioIndicadores)) 
			return -1;
		else 
			return 1;
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		criterio.evaluarRequisitosEn(this, empresa, repoIndicadores);		
	}
	
}
