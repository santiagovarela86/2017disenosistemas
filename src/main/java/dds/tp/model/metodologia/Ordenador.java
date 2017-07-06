package dds.tp.model.metodologia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Ordenador {

	public ArrayList<ResultadoAnalisis> getResultados(ArrayList<Empresa> empresasQueConvieneInvertir,
			ArrayList<CondicionPriorizar> condicionesQuePriorizan, RepositorioIndicadores repoIndicadores) {

		ArrayList<ResultadoAnalisis> resultadosOrdenados = new ArrayList<>();
		List<List<Empresa>> listasIntermedias = new ArrayList<List<Empresa>>();
		
		//GENERO LAS LISTAS INTERMEDIAS POR CADA CONDICION PRIORIZANTE
		condicionesQuePriorizan.forEach(condicion -> 
			listasIntermedias.add(generarListaOrdenada(empresasQueConvieneInvertir, condicion, repoIndicadores)));
		
		//GENERO LA LISTA FINAL A PARTIR DE LAS LISTAS INTERMEDIAS
		resultadosOrdenados = generarListaFinal(empresasQueConvieneInvertir, listasIntermedias);
		
		return resultadosOrdenados;
	}
	
	public ArrayList<Empresa> generarListaOrdenada(List<Empresa> lista, CondicionPriorizar cond, RepositorioIndicadores repoIndicadores){
		//ME COPIO LA LISTA ORIGINAL
		ArrayList<Empresa> listaOrdenada = new ArrayList<Empresa>(lista);

		//ORDENO LA COPIA DE LA LISTA SEGUN LA CONDICION PRIORIZANTE
		Collections.sort(listaOrdenada, (empresa1, empresa2) -> cond.evaluarInt(empresa1, empresa2, repoIndicadores));
		
		return listaOrdenada;
	}
	
	public ArrayList<ResultadoAnalisis> generarListaFinal(ArrayList<Empresa> empresas, List<List<Empresa>> listasIntermedias){
		ArrayList<ResultadoAnalisis> resultadosOrdenados = new ArrayList<>();
		
		//POR CADA EMPRESA OBTENGO SUS PUNTOS
		empresas.forEach(empresa -> resultadosOrdenados.add(generarResultadoEmpresa(empresa, listasIntermedias)));
		
		return resultadosOrdenados;
	}
	
	public ResultadoAnalisis generarResultadoEmpresa(Empresa empresa, List<List<Empresa>> listasIntermedias){
		ArrayList<Integer> posiciones = new ArrayList<Integer>();
		
		//POR CADA LISTA OBTENGO LA POSICION DE LA EMPRESA
		listasIntermedias.forEach(lista -> posiciones.add(lista.indexOf(empresa)));
		
		//EL PUNTAJE DE CADA EMPRESA ES LA SUMA DE LAS POSICIONES EN CADA LISTA
		return new ResultadoAnalisis(posiciones.stream().mapToInt(Integer::intValue).sum(), empresa, ""); //JUSTIFICACION?
	}

}
