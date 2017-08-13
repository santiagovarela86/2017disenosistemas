package dds.tp.model.builders;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Metodologia;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;

public class MetodologiaBuilder {

	private String nombre;
	private ArrayList<CondicionTaxativa> condicionesTaxativas = new ArrayList<>();
	private ArrayList<CondicionPriorizante> condicionesQuePriorizan = new ArrayList<>();
	
	public MetodologiaBuilder setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public MetodologiaBuilder setCondicionesQuePriorizan(ArrayList<CondicionPriorizante> condicionesQuePriorizan) {
		this.condicionesQuePriorizan = condicionesQuePriorizan;
		return this;
	}
	
	public MetodologiaBuilder setCondicionesTaxativas(ArrayList<CondicionTaxativa> condicionesTaxativas) {
		this.condicionesTaxativas = condicionesTaxativas;
		return this;
	}
	
	public MetodologiaBuilder agregarCondTaxativa(CondicionTaxativa condTaxativa) {
		this.condicionesTaxativas.add(condTaxativa);
		return this;
	}
	
	public MetodologiaBuilder agregarCondPriorizar(CondicionPriorizante condPriorizar) {
		this.condicionesQuePriorizan.add(condPriorizar);
		return this;
	}
	
	public Metodologia build() {
		return new Metodologia(nombre, condicionesTaxativas, condicionesQuePriorizan);
	}

	public List<Condicion> getAllCondiciones() {
		ArrayList<Condicion> allCondiciones = new ArrayList<>(this.condicionesQuePriorizan);
		allCondiciones.addAll(condicionesTaxativas);
		return allCondiciones;
	}
	
	public void borrarCondiciones() {
		this.condicionesQuePriorizan.clear();
		this.condicionesTaxativas.clear();
	}
}
