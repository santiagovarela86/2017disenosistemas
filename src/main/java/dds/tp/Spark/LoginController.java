package dds.tp.Spark;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;

public class LoginController {

	public static Object mostrarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/mostrarLogin.vm");
	}
	
	public static Object redirectLogin(Request request, Response response) {
		response.redirect("/login");
		return null;
	}
	
	public static Object procesarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		String user = request.queryParams("user");
		String password = request.queryParams("password");
				
		if (SessionController.autenticar(user, password)) {
			request.session().attribute("currentUser", user);
			response.redirect("/stockApp/pantallaPrincipal");
		} else {
			model.put("message", "Credenciales incorrectas.<br>Intente nuevamente.");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		
		return null;
	}

	public static Object procesarLogout(Request request, Response response) {
		request.session().removeAttribute("currentUser");
		request.session().invalidate();
		response.redirect("/login");
		return null;
	}
	
}
