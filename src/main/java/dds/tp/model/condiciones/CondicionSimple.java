package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionSimple extends Condicion {

	private Double valorLimite;
	private int aniosACumplir;
	private Indicador indicador;
	private Comparador comparador;
	
	public CondicionSimple(String nombre, String descripcion, Indicador indicador, Comparador comparador,Double valorLimite, int aniosACumplir) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.valorLimite = valorLimite;
		this.aniosACumplir = aniosACumplir;
	}

	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores){
		Anual periodoEvaluar = new Anual(Year.now().getValue());
		for (int i = 0; i < aniosACumplir; i++) {
			if(!comparador.comparar(indicador.evaluar(empresa.getBalance(periodoEvaluar),repoIndicadores),valorLimite)){
				return false;
			}
		}
		return true;
	}

}
