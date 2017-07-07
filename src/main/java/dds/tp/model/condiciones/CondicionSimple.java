package dds.tp.model.condiciones;

import java.time.Year;

import dds.tp.excepciones.PeriodosCantBeCero;
import dds.tp.model.CondicionTaxativa;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionSimple extends CondicionTaxativa {

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
		if(aniosACumplir==0)
			throw new PeriodosCantBeCero();
	}

	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores){
		Anual periodoEvaluar = new Anual(Year.now().getValue());
		for (int i = 0; i < aniosACumplir; i++) {
			if(!comparador.comparar(indicador.evaluar(empresa.getBalance(periodoEvaluar),repoIndicadores),valorLimite)){
				return false;
			}
			periodoEvaluar = periodoEvaluar.anioAnterior();
		}
		return true;
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		Anual periodoEvaluar = new Anual(Year.now().getValue());
		for (int i = 0; i < aniosACumplir; i++) {
			indicador.evaluar(empresa.getBalance(periodoEvaluar),repoIndicadores);
			periodoEvaluar = periodoEvaluar.anioAnterior();
		}
	}

}
