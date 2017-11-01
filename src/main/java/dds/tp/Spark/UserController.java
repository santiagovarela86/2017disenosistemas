package dds.tp.Spark;

import java.util.ArrayList;
import java.util.List;

import dds.tp.excepciones.UsuarioNoExistente;

public class UserController {
	
	@SuppressWarnings("serial")
	static List<User> usuarios = new ArrayList<User>(){{
		add(new User("santiago","santiago"));
		add(new User("ezequiel","ezequiel"));
		add(new User("leonardo","leonardo"));
	}};

	public static Boolean autenticar(String userIngresado, String passwordIngresado){
		
		User user;
		
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

	public static User buscarUsuario(String userBuscado) throws UsuarioNoExistente {
	
		for(User user : usuarios) {
	        if(user.getNombre().equals(userBuscado)) {
	            return user;
	        }
	    }
		
	    throw new UsuarioNoExistente("No existe el usuario: " + userBuscado);		

	}
	
}
