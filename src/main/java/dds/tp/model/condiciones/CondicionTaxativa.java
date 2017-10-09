package dds.tp.model.condiciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.comparadores.Comparador;

import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@DiscriminatorValue("condTaxativa")
public abstract class CondicionTaxativa extends Condicion {
	
	protected Double valorLimite;
	
	public CondicionTaxativa() {
		// TODO Auto-generated constructor stub
	}
	
	public CondicionTaxativa(String nombre, String descripcion, Comparado indicador, Comparador comparador,
			int periodosHaciaAtras, Double valorLimite) {		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras);
		this.valorLimite = valorLimite;
	}
	
	public Double getValorLimite() {
		return valorLimite;
	}
	
	public abstract boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores);
}
