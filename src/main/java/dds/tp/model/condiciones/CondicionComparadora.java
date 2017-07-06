package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.model.Condicion;
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
	}
	
	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		Anual periodoAEvaluar = new Anual(Year.now().getValue());
		for (int i = 1; i <= periodosHaciaAtras; i++) {
			if(!comparador.comparar(indicador.evaluar(empresa1.getBalance(periodoAEvaluar),repoIndicadores), 
					indicador.evaluar(empresa2.getBalance(periodoAEvaluar),repoIndicadores))){
				return false;
			}
			periodoAEvaluar = periodoAEvaluar.anioAnterior();
		}
		return true;
	}
}