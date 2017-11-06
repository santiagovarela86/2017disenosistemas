package dds.tp.main;

import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class InicializarDB {
		
	private static RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
	private static RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	public static void main(String[] args) {
		guardarUsuarios();
		Usuario usuarioDefault = repoUsuarios.getUsuario("default");
		guardarIndicadores(usuarioDefault);
		guardarMetodologias(usuarioDefault);
		System.exit(0);
	}
	
	private static void guardarUsuarios() {
		repoUsuarios.addUsuario(new Usuario("default", ""));
		repoUsuarios.addUsuario(new Usuario("santiago", "santiago"));
		repoUsuarios.addUsuario(new Usuario("ezequiel", "ezequiel"));
		repoUsuarios.addUsuario(new Usuario("leonardo", "leonardo"));
		repoUsuarios.guardarUsuarios(repoUsuarios.getUsuarios());
	}
	
	@SuppressWarnings("deprecation")
	private static void guardarIndicadores(Usuario usuarioDefault) {
		repositorioIndicadores.cargarPredeterminados(usuarioDefault);
		repositorioIndicadores.guardarIndicadores(repositorioIndicadores.getIndicadores());	
	}
	
	@SuppressWarnings("deprecation")
	private static void guardarMetodologias(Usuario usuarioDefault) {
		RepositorioMetodologias repositorioMetodologias = new RepositorioMetodologias();
		repositorioMetodologias.cargarPredeterminados(repositorioIndicadores, usuarioDefault);
		//Hacer un guardar metodologia q reciba una lista como en indicadores
		repositorioMetodologias.guardarMetodologia(repositorioMetodologias.getMetodologias().get(0));
	}


}
