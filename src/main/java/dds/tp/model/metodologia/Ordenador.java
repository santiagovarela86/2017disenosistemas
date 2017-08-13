package dds.tp.model.metodologia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Ordenador {

	public List<ResultadoAnalisis> getResultados(List<Empresa> empresasQueConvieneInvertir,
			List<CondicionPriorizante> condicionesQuePriorizan, RepositorioIndicadores repoIndicadores) {

		List<ResultadoAnalisis> resultadosOrdenados = new ArrayList<>();
		List<List<Empresa>> listasIntermedias = new ArrayList<List<Empresa>>();
		
		//GENERO LAS LISTAS INTERMEDIAS POR CADA CONDICION PRIORIZANTE
		condicionesQuePriorizan.forEach(condicion -> 
			listasIntermedias.add(generarListaOrdenada(empresasQueConvieneInvertir, condicion, repoIndicadores)));
		
		//GENERO LA LISTA FINAL A PARTIR DE LAS LISTAS INTERMEDIAS
		resultadosOrdenados = generarListaFinal(empresasQueConvieneInvertir, listasIntermedias);
		
		return resultadosOrdenados;
	}
	
	public List<Empresa> generarListaOrdenada(List<Empresa> lista, CondicionPriorizante cond, RepositorioIndicadores repoIndicadores){
		//ME COPIO LA LISTA ORIGINAL
		ArrayList<Empresa> listaOrdenada = new ArrayList<Empresa>(lista);

		//ORDENO LA COPIA DE LA LISTA SEGUN LA CONDICION PRIORIZANTE
		Collections.sort(listaOrdenada, (empresa1, empresa2) -> cond.evaluarInt(empresa1, empresa2, repoIndicadores));
		
		return listaOrdenada;
	}
	
	private List<ResultadoAnalisis> generarListaFinal(List<Empresa> empresas, List<List<Empresa>> listasIntermedias){
		ArrayList<ResultadoAnalisis> resultadosOrdenados = new ArrayList<>();
		
		//POR CADA EMPRESA OBTENGO SUS PUNTOS
		empresas.forEach(empresa -> resultadosOrdenados.add(generarResultadoEmpresa(empresa, listasIntermedias)));
		
		Collections.sort(resultadosOrdenados, (resultado1, resultado2) -> resultado1.esMayorQue(resultado2));
		
		return resultadosOrdenados;
	}
	
	private ResultadoAnalisis generarResultadoEmpresa(Empresa empresa, List<List<Empresa>> listasIntermedias){
		int puntaje = 0;
		
		//POR CADA LISTA OBTENGO LA POSICION DE LA EMPRESA
		for (List<Empresa> list : listasIntermedias) {
			puntaje += (list.size() - list.indexOf(empresa));
		}
		
		//EL PUNTAJE DE CADA EMPRESA ES LA SUMA DE LAS POSICIONES EN CADA LISTA
		return new ResultadoAnalisis(puntaje, empresa, "Ha pasado todas las condiciones correctamente");
	}

}
