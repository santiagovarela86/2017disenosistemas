package dds.tp.main;

import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class CargarUsuariosPredeterminados {
	
	public static void main(String[] args) {
		RepositorioUsuarios repositorio = new RepositorioUsuarios();
		repositorio.cargarUsuariosCargados();
		
		Usuario santiago = new Usuario("santiago", "santiago");
		Usuario ezequiel = new Usuario("ezequiel", "ezequiel");
		Usuario leonardo = new Usuario("leonardo", "leonardo");
		
		repositorio.addUsuario(santiago);
		repositorio.addUsuario(ezequiel);
		repositorio.addUsuario(leonardo);
		
		repositorio.guardarUsuarios(repositorio.getUsuarios());
		
		System.exit(0);
	}

}
