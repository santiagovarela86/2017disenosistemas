package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.excepciones.NoHayCondiciones;
import dds.tp.model.metodologia.Filtro;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Metodologia {

	private String nombre;
	private ArrayList<CondicionTaxativa> condicionesTaxativas;
	private ArrayList<CondicionPriorizar> condicionesQuePriorizan;
	
	public Metodologia(String nombre, ArrayList<CondicionTaxativa> condicionesTaxativas, ArrayList<CondicionPriorizar> condicionesQuePriorizan) {
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

	public ArrayList<ResultadoAnalisis> evaluarEn(List<Empresa> empresas, RepositorioIndicadores repoIndicadores){
		ArrayList<ResultadoAnalisis> resultadosNegativos = new ArrayList<>();
		ArrayList<ResultadoAnalisis> resultadosPositivos = new ArrayList<>();
		ArrayList<ResultadoAnalisis> resultadosTotales = new ArrayList<>();
		ArrayList<Empresa> empresasQueConvieneInvertir = new ArrayList<>(empresas);
		for (CondicionTaxativa condicion : condicionesTaxativas) {
			resultadosNegativos
			.addAll(new Filtro().getResultadosNegativos(empresasQueConvieneInvertir, condicion, repoIndicadores));
			empresasQueConvieneInvertir = new Filtro().getEmpresasQueCumplen(empresasQueConvieneInvertir, condicion, repoIndicadores); 
		}
		resultadosPositivos = (ArrayList<ResultadoAnalisis>) new Ordenador().getResultados(empresasQueConvieneInvertir, condicionesQuePriorizan,repoIndicadores);
		resultadosTotales.addAll(resultadosPositivos);
		resultadosTotales.addAll(resultadosNegativos);
		return resultadosTotales;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}

	public List<Condicion> getCondiciones() {
		ArrayList<Condicion> allCondiciones = new ArrayList<>(this.condicionesQuePriorizan);
		allCondiciones.addAll(condicionesTaxativas);
		return allCondiciones;
	}
}
