package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.calculador.Expresion;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Indicador;
import dds.tp.parsertools.Parser;

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
		return this.indicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
	}
	
	public void cargarPredeterminados() {
		this.addIndicador(new Indicador("Ingreso Neto", new Expresion(new Parser().parsear("ingresoNetoEnOperacionesContinuas+ingresoNetoEnOperacionesContinuas"))));
		this.addIndicador(new Indicador("Razon Corriente", new Expresion(new Parser().parsear("activoCorriente/pasivoCorriente"))));
		this.addIndicador(new Indicador("ROA", new Expresion(new Parser().parsear("utilidadBruta/activoTotal"))));
		this.addIndicador(new Indicador("Endeudamiento", new Expresion(new Parser().parsear("pasivoTotalTerceros/activoTotal"))));
	}
	
}
