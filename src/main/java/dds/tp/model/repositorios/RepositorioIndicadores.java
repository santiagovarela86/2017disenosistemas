package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Indicador;

public class RepositorioIndicadores {

	private List<Indicador> indicadores;
	
	public RepositorioIndicadores() {
		super();
		this.indicadores = new ArrayList<>();
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void addIndicador(Indicador indc) throws ElementoYaExiste {
		if(this.contieneIndicador(indc.getNombre())) {
			throw new ElementoYaExiste("Ya existe un indicador con este nombre");
		}
		this.indicadores.add(indc);
	}
	
	public boolean contieneIndicador(String nombre) {
		return this.indicadores.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}

	public Indicador getIndicador(String nombre) {
		if(!this.contieneIndicador(nombre)){
			throw new ElementoNotFound("No existe el indicador " + nombre);
		}
		return this.indicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
	}
	
	public void cargarPredeterminados() {
		this.addIndicador(new Indicador("Ingreso Neto", "ingresoNetoEnOperacionesContinuas+ingresoNetoEnOperacionesContinuas"));
		this.addIndicador(new Indicador("Razon Corriente", "activoCorriente/pasivoCorriente"));
		this.addIndicador(new Indicador("ROA", "utilidadBruta/activoTotal"));
		//Pongo indicador antes xq sino tiene la cuenta se llama recursivamente el mismo indicador y tira stackoverflow
		//Normalmente un indicador no se llama igual q el calculo...
		this.addIndicador(new Indicador("Indicador ROE", "roe"));
		this.addIndicador(new Indicador("Indicador Endeudamiento", "endeudamiento"));
		this.addIndicador(new Indicador("Indicador Margen", "margen"));
	}
	
}
