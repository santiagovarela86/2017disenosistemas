package dds.tp.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dds.tp.SparkControllers.SessionController;
import dds.tp.memcache.MemoriaCache;
import dds.tp.model.LectorCuentas;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class InicializarDB {
		
	private static RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
	private static RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
	private static RepositorioEmpresas repoEmpresas;
	public static void main(String[] args) {
		guardarCuentas();
		guardarUsuarios();
		Usuario usuarioDefault = repoUsuarios.getUsuario("default");
		Usuario santiago = repoUsuarios.getUsuario("santiago");
		guardarIndicadores(usuarioDefault);
		guardarMetodologias(usuarioDefault);
		guardarMetodologias(santiago);
		MemoriaCache memCache = new MemoriaCache();
		memCache.inicializarCache(repoEmpresas.getEmpresas(), repositorioIndicadores, usuarioDefault);
		System.exit(0);
	}
	
	private static void guardarCuentas() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testsMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testsMetodologia.txt"))));
			repoEmpresas.guardarEmpresas(repoEmpresas.getEmpresas());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void guardarUsuarios() {
		repoUsuarios.addUsuario(new Usuario("default", SessionController.Hash("asd")));
		repoUsuarios.addUsuario(new Usuario("santiago", SessionController.Hash("santiago")));
		repoUsuarios.addUsuario(new Usuario("ezequiel", SessionController.Hash("ezequiel")));
		repoUsuarios.addUsuario(new Usuario("leonardo", SessionController.Hash("leonardo")));
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
