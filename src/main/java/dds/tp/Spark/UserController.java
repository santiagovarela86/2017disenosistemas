package dds.tp.Spark;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import dds.tp.excepciones.UsuarioNoExistente;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class UserController {
	
	public static Boolean autenticar(String userIngresado, String passwordIngresado){
		Usuario user;
		String hashDelPassword = null;
		try {
			user = buscarUsuario(userIngresado);
			hashDelPassword = UserController.Hash(passwordIngresado);
			if (user.getPassword().equals(hashDelPassword))
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

	public static String Hash(String pass) {
		String hash = null;
		String secretKey = "stockApp2017";
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			hash = Base64.encodeBase64String(sha256_HMAC.doFinal(pass.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
	
}
