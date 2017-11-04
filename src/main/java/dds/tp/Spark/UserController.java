package dds.tp.Spark;

import dds.tp.excepciones.UsuarioNoExistente;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class UserController {
	
	public static Boolean autenticar(String userIngresado, String passwordIngresado){
		
		Usuario user;
		
		try {
			user = buscarUsuario(userIngresado);
		} catch (UsuarioNoExistente e) {
			return false;
		}
		
		if (user.getPassword().equals(passwordIngresado)) {
			return true;
		}
		
		return false;
	}

	public static Usuario buscarUsuario(String userBuscado) throws UsuarioNoExistente {
		
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.cargarUsuariosCargados();
	
		for(Usuario user : repoUsuarios.getUsuarios()) {
	        if(user.getNombre().equals(userBuscado)) {
	            return user;
	        }
	    }
		
	    throw new UsuarioNoExistente("No existe el usuario: " + userBuscado);		

	}
	
}
