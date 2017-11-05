package dds.tp.main;

import java.util.ArrayList;
import java.util.List;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class CargarUsuariosPredeterminados {
	
	public static void main(String[] args) {
		RepositorioUsuarios repositorio = new RepositorioUsuarios();
		repositorio.cargarUsuariosCargados();
		
		List<Usuario> usuariosPredeterminados = new ArrayList<Usuario>();
		
		usuariosPredeterminados.add(new Usuario("default", ""));
		usuariosPredeterminados.add(new Usuario("santiago", "santiago"));
		usuariosPredeterminados.add(new Usuario("ezequiel", "ezequiel"));
		usuariosPredeterminados.add(new Usuario("leonardo", "leonardo"));
		
		for (Usuario u : usuariosPredeterminados){
			try {
				repositorio.addUsuario(u);
			} catch (ElementoYaExiste e){
				System.out.println(e.getMessage());
			}
		}

		repositorio.guardarUsuarios(repositorio.getUsuarios());
		
		System.exit(0);
	}

}
