package dds.tp.model.condiciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.criterios.Criterio;

import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@DiscriminatorValue("condTaxativa")
public class CondicionTaxativa extends Condicion {
	
	private Double valorLimite;
	
	@OneToOne
	private Criterio criterio;
	
	public CondicionTaxativa(String nombre, String descripcion, Comparado indicador, Comparador comparador,
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
}
