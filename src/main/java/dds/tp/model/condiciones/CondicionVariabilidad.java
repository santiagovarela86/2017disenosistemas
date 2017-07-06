package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionVariabilidad extends Condicion {
	
	private int periodosHaciaAtras;
	private Indicador indicador;
	private Comparador comparador;

	public CondicionVariabilidad(String nombre,String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
		
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores){
		Anual periodoEvaluar = new Anual(Year.now().getValue()-periodosHaciaAtras+1);
		Double valorAUsar =  indicador.evaluar(empresa.getBalance(periodoEvaluar), repoIndicadores);
		int vecesCumplido = 0;
		for (int i = 1; i < periodosHaciaAtras; i++) {
			periodoEvaluar = periodoEvaluar.anioSiguiente();
			Double valorSiguiente = indicador.evaluar(empresa.getBalance(periodoEvaluar), repoIndicadores);
			if(comparador.comparar(valorAUsar, valorSiguiente))
				vecesCumplido++;
			valorAUsar = valorSiguiente;
		}
		return vecesCumplido>periodosHaciaAtras*0.70;
	}

}
