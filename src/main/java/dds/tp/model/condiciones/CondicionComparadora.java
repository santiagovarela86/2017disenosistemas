package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.excepciones.PeriodosCantBeCero;
import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

//ESTA SE USA PARA ORDENAR
public class CondicionComparadora extends Condicion {

	private int periodosHaciaAtras;
	private Indicador indicador;
	private Comparador comparador;
	
	public CondicionComparadora(String nombre,String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.periodosHaciaAtras = periodosHaciaAtras;
		if(periodosHaciaAtras==0)
			throw new PeriodosCantBeCero();
	}
	
	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		Anual periodoAEvaluar = new Anual(Year.now().getValue());
		
		boolean seCumple = true;
		
		for (int i = 1; i <= periodosHaciaAtras; i++) {
			seCumple = seCumple && comparar(empresa1, empresa2,repoIndicadores,periodoAEvaluar);
  			periodoAEvaluar = periodoAEvaluar.anioAnterior();
		}

		return seCumple;
	}

	private boolean comparar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores, Anual anio) {
		return comparador.comparar(obtenerResultado(empresa1, anio,repoIndicadores), 
								   obtenerResultado(empresa2, anio, repoIndicadores));
	}
	
	private Double obtenerResultado(Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores){
		Balance balance = empresa.getBalance(anio);
		return indicador.evaluar(balance, repoIndicadores);
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		Anual periodoAEvaluar = new Anual(Year.now().getValue());
		for (int i = 1; i <= periodosHaciaAtras; i++) {
			indicador.evaluar(empresa.getBalance(periodoAEvaluar),repoIndicadores);
			periodoAEvaluar = periodoAEvaluar.anioAnterior();
		}
	}
}
