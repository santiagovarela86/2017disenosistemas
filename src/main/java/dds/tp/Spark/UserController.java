package dds.tp.Spark;

import dds.tp.excepciones.UsuarioNoExistente;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class UserController {
	
	public static Boolean autenticar(String userIngresado, String passwordIngresado){
		Usuario user;
		try {
			user = buscarUsuario(userIngresado);
			if (user.getPassword().equals(passwordIngresado))
				return true;
			else
				return false;
		} catch (UsuarioNoExistente e) {
			return false;
		}
	}

	public static Usuario buscarUsuario(String userBuscado) throws UsuarioNoExistente {
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.inicializar();
		if(repoUsuarios.contieneUsuario(userBuscado)){
			return repoUsuarios.getUsuario(userBuscado);
		}
		else
			throw new UsuarioNoExistente("No existe el usuario: " + userBuscado);		
	}
	
}
