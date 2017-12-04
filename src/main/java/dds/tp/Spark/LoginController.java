package dds.tp.Spark;

import static dds.tp.Spark.Router.repositorioUsuario;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import dds.tp.Spark.util.Path;
import dds.tp.Spark.util.ViewUtil;
import dds.tp.model.Usuario;
import spark.Request;
import spark.Response;
import spark.Route;


public class LoginController {

    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("message","Ingrese usuario y contraseña");
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

	public static Route handleLoginPost = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		String nombre = request.queryParams("nombre");
		String password = request.queryParams("password");

		if (getUserAuthenticate(nombre, password) == null) {
			model.put("message","usuario no existe, Intente nuevamente");
			return ViewUtil.render(request, model, Path.Template.LOGIN);
		}

		model.put("usuario", getUserAuthenticate(nombre, password));
		request.session().attribute("usuario", getUserAuthenticate(nombre, password));
		return ViewUtil.render(request, model, Path.Template.PANTALLA_PRINCIPAL);
	};

	private static Usuario getUserAuthenticate(String nombre, String password) {
		return repositorioUsuario.buscarUsuarioLogueado(nombre, password);
	}

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("usuario");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };

    //Me aseguro que el Usuario, este logueado y bloqueo la entrada a cualquier PATH VALIDO.
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("usuario") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            halt(401, "Logueate, no te hagas del vivo!!");
        }
    };

}
