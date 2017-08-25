package dds.tp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.excepciones.NoHayCondiciones;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.metodologia.Filtro;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Metodologia {

	private String nombre;
	private ArrayList<CondicionTaxativa> condicionesTaxativas;
	private ArrayList<CondicionPriorizante> condicionesQuePriorizan;
	
	public Metodologia(String nombre, ArrayList<CondicionTaxativa> condicionesTaxativas, ArrayList<CondicionPriorizante> condicionesQuePriorizan) {
		if(nombre == null || nombre.equalsIgnoreCase(""))
			throw new ElementNotLoad("No se aceptan nombres vacios");
		if(condicionesQuePriorizan.isEmpty() && condicionesTaxativas.isEmpty())
			throw new NoHayCondiciones();
		this.nombre = nombre;
		this.condicionesQuePriorizan = condicionesQuePriorizan;
		this.condicionesTaxativas = condicionesTaxativas;
	}
	
	public String getNombre() {
		return nombre;
	}

	public List<ResultadoAnalisis> evaluarEn(List<Empresa> empresas, RepositorioIndicadores repoIndicadores){
		List<ResultadoAnalisis> resultadosTemporales = empresas.stream()
			.filter(empresa -> this.getCondiciones().stream().anyMatch(cond -> !cond.empresaPuedeSerEvaluada(empresa, repoIndicadores)))
			.map(elem -> new ResultadoAnalisis(0, elem, "Esta empresa no tiene los elementos suficientes"))
			.collect(Collectors.toList());
		
		this.removerEmpresasYaAnalizadas(empresas, resultadosTemporales);
		
		condicionesTaxativas.forEach(cond -> 
					{resultadosTemporales.addAll(new Filtro().getResultadosNegativos(empresas, cond, repoIndicadores));
					this.removerEmpresasYaAnalizadas(empresas, resultadosTemporales);});
		
		resultadosTemporales.addAll(new Ordenador().getResultados(empresas, condicionesQuePriorizan, repoIndicadores));
		Collections.reverse(resultadosTemporales);
		return resultadosTemporales;
	}
	
	public List<Condicion> getCondiciones() {
		ArrayList<Condicion> allCondiciones = new ArrayList<>(this.condicionesQuePriorizan);
		allCondiciones.addAll(condicionesTaxativas);
		return allCondiciones;
	}
	
	public void removerEmpresasYaAnalizadas(List<Empresa> empresas, List<ResultadoAnalisis> resultados) {
		resultados.stream()
				.map(analisis -> analisis.getEmpresa())
				.forEach(empr -> empresas.remove(empr));
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
