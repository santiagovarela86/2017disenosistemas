package dds.tp.model.condiciones;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.criterios.Criterio;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionTaxativa extends Condicion {
	
	private Double valorLimite;
	private Criterio criterio;
	
	public CondicionTaxativa(String nombre, String descripcion, Indicador indicador, Comparador comparador,
			int periodosHaciaAtras, Criterio criterio, Double valorLimite) {
		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras);
		this.setCriterio(criterio);
		this.setValorLimite(valorLimite);
	}

	public Double getValorLimite() {
		return valorLimite;
	}

	public void setValorLimite(Double valorLimite) {
		this.valorLimite = valorLimite;
	}
	
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return criterio.evaluar(this, empresa, repoIndicadores);
	}

	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		criterio.evaluarRequisitosEn(this, empresa, repoIndicadores);	
	}

}
