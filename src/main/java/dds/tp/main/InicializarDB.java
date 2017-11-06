package dds.tp.main;

import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class InicializarDB {
		
	private static RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
	public static void main(String[] args) {
		guardarUsuarios();
		guardarIndicadores();
		guardarMetodologias();
		System.exit(0);
	}
	
	private static void guardarUsuarios() {
		RepositorioUsuarios repositorio = new RepositorioUsuarios();
		repositorio.addUsuario(new Usuario("default", ""));
		repositorio.addUsuario(new Usuario("santiago", "santiago"));
		repositorio.addUsuario(new Usuario("ezequiel", "ezequiel"));
		repositorio.addUsuario(new Usuario("leonardo", "leonardo"));
		repositorio.guardarUsuarios(repositorio.getUsuarios());
	}
	
	@SuppressWarnings("deprecation")
	private static void guardarIndicadores() {
		repositorioIndicadores.cargarPredeterminados();
	}
	
	@SuppressWarnings("deprecation")
	private static void guardarMetodologias() {
		RepositorioMetodologias repositorioMetodologias = new RepositorioMetodologias();
		repositorioMetodologias.cargarPredeterminados(repositorioIndicadores);
	}


}
