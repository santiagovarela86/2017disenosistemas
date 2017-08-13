package dds.tp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.excepciones.NoHayCondiciones;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.metodologia.ControladorRequisitos;
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
		List<ResultadoAnalisis> resultadosNegativos = new ArrayList<>();
		List<ResultadoAnalisis> resultadosPositivos = new ArrayList<>();
		List<ResultadoAnalisis> resultadosTotales = new ArrayList<>();
		List<Empresa> empresasQueConvieneInvertir = new ArrayList<>(empresas);
		
		resultadosNegativos =  new ControladorRequisitos().getEmpresasQueNoCumplenLosRequisitos(empresas, getCondiciones(), repoIndicadores);
		
		for (CondicionTaxativa condicion : condicionesTaxativas) {
			agregarResultadosNegativos(resultadosNegativos,empresasQueConvieneInvertir,condicion,repoIndicadores);
		}
		
		removerEmpresasQueYaNoConvieneInvertirDesdeResultados(empresasQueConvieneInvertir, resultadosNegativos);
	
		resultadosPositivos = new Ordenador().getResultados(empresasQueConvieneInvertir,condicionesQuePriorizan,repoIndicadores);
		resultadosTotales.addAll(resultadosPositivos);
		resultadosTotales.addAll(resultadosNegativos);
		return resultadosTotales;
	}
	
	private void agregarResultadosNegativos(List<ResultadoAnalisis> resultadosN, List<Empresa> empresas, CondicionTaxativa c, RepositorioIndicadores repoI){
		resultadosN.addAll(new Filtro().getResultadosNegativos(empresas, c, repoI)) ;
	}

	public List<Condicion> getCondiciones() {
		ArrayList<Condicion> allCondiciones = new ArrayList<>(this.condicionesQuePriorizan);
		allCondiciones.addAll(condicionesTaxativas);
		return allCondiciones;
	}
	
	private void removerEmpresasQueYaNoConvieneInvertirDesdeResultados(List<Empresa> empresas, List<ResultadoAnalisis> resultadosNegativos) {
		resultadosNegativos.stream().forEach(e -> empresas.remove(e.getEmpresa()));
		Collections.reverse(resultadosNegativos);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
}
