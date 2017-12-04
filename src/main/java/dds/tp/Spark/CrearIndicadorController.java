package dds.tp.Spark;

import java.util.HashMap;
import java.util.Map;

import dds.tp.Spark.util.Path;
import dds.tp.Spark.util.ViewUtil;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import spark.Request;
import spark.Response;
import spark.Route;
import static dds.tp.Spark.Router.repositorioUsuario;
import static dds.tp.Spark.Router.repoEmpresas;
import static dds.tp.Spark.Router.memCache;

public class CrearIndicadorController {

	
	
	public static Route crearIndicador = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);		
		
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		model.put("usuario", request.session().attribute("usuario"));
		return ViewUtil.render(request, model, Path.Template.CREAR_INDICADOR);
	};

	public static Route crearIndicadorEspecifico = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		Usuario usuario = request.session().attribute("usuario");
		
		repositorioUsuario.inicializarIndicadoresYMetodologias(usuario);
		usuario.inicializarRepos();
		String nombreIndicador = request.queryParams("nombreIndicador");
		String expresionIndicador = request.queryParams("expresionIndicador");
		model.put("usuario", request.session().attribute("usuario"));
		try {
			Indicador indicador = new Indicador(nombreIndicador, expresionIndicador, usuario);
			usuario.addIndicador(indicador);
			memCache.seCreoNuevoIndicador(indicador, repoEmpresas.getEmpresas(), usuario.getRepoIndicadores(),usuario);
			model.put("message", "Indicador \"" + nombreIndicador + "\" agregado con éxito.<br>Expresión: \"" + expresionIndicador + "\".");
			return ViewUtil.render(request, model, Path.Template.CREAR_INDICADOR);
		} catch (SintaxisIncorrecta e1) {
			model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
			return ViewUtil.render(request, model, Path.Template.CREAR_INDICADOR);
		} catch (ElementoYaExiste e2) {
			model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
			return ViewUtil.render(request, model, Path.Template.CREAR_INDICADOR);
		}
	};
	
	
}
