package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.metodologia.Filtro;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Metodologia {

	private String nombre;
	private ArrayList<CondicionTaxativa> condicionesTaxativas;
	private ArrayList<CondicionPriorizar> condicionesQuePriorizan;
	
	public Metodologia(String nombre, ArrayList<CondicionTaxativa> condicionesTaxativas, ArrayList<CondicionPriorizar> condicionesQuePriorizan) {
		this.nombre = nombre;
		this.condicionesQuePriorizan = condicionesQuePriorizan;
		this.condicionesTaxativas = condicionesTaxativas;
	}
	
	public String getNombre() {
		return nombre;
	}

	/*public List<ResultadoAnalisis> evaluarEn(List<Empresa> empresas, RepositorioIndicadores repoIndicadores){
		ArrayList<ResultadoAnalisis> resultadosPositivos = new ArrayList<>();
		ArrayList<ResultadoAnalisis> resultadosTotales = new ArrayList<>();
		ArrayList<Empresa> empresasQueConvieneInvertir = new ArrayList<>(empresas);
		for (CondicionTaxativa condicion : condicionesTaxativas) {
			resultadosNegativos
			.addAll(new Filtro().getResultadosNegativos(empresasQueConvieneInvertir, condicion, repoIndicadores));
			empresasQueConvieneInvertir = new Filtro().getEmpresasQueCumplen(empresasQueConvieneInvertir, condicion, repoIndicadores); 
		}
		resultadosPositivos = new Ordenador().getResultados(empresasQueConvieneInvertir, condicionesQuePriorizan,repoIndicadores);
		resultadosTotales.addAll(resultadosPositivos);
		resultadosTotales.addAll(resultadosNegativos);
		return resultadosTotales;
		
	}*/
}
