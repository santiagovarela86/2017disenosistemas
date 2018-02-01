package dds.tp.SparkControllers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;
import spark.Spark;

public class SessionController {
	
	public static Object validarUsuarioLogueado(Request request, Response response) {
		
		String supuestoUsuario = request.session().attribute("currentUser");
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.inicializar();
		
		if (supuestoUsuario == null || !repoUsuarios.contieneUsuario(supuestoUsuario)){
			response.redirect("/login");
			Spark.halt();
		}
		
		return null;
	}
	
	public static Boolean autenticar(String userIngresado, String passwordIngresado){
		Usuario user;
		String hashDelPassword = null;
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		if (repoUsuarios.inicializar().contieneUsuario(userIngresado)) {
			user = repoUsuarios.getUsuario(userIngresado);
			hashDelPassword = SessionController.Hash(passwordIngresado);			
			return user.getPassword().equals(hashDelPassword);
		} else {
			return false;
		}		
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
