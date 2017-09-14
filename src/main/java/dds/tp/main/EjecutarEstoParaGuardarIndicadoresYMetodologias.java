package dds.tp.main;

import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class EjecutarEstoParaGuardarIndicadoresYMetodologias {

	public static void main(String[] args) {
		RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
		repositorioIndicadores.cargarPredeterminados();
		repositorioIndicadores.guardarIndicadores(repositorioIndicadores.getIndicadores());
		
		RepositorioMetodologias repositorioMetodologias = new RepositorioMetodologias();
		repositorioMetodologias.cargarPredeterminados(repositorioIndicadores);
		//No es como el otro total hay una solo metodologia, entonces recbe solo la q hay
		repositorioMetodologias.guardarMetodologia(repositorioMetodologias.getMetodologias().get(0));
		
		//CAMBIAR A VALIDATE
	}

}
