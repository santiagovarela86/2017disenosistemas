package dds.tp.SparkControllers;

import java.util.HashMap;
import java.util.Map;

import dds.tp.Spark.Utils;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.memcache.MemoriaCache;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class CrearIndicadorController {
	
	private static MemoriaCache memCache = new MemoriaCache();

	public static Object crearIndicador(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
	
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/crearIndicador.vm");
	}

	public static Object crearIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.cargarEmpresasGuardadas();
		//repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();

		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.inicializar();		
		Usuario user = repoUsuarios.getUsuario(request.session().attribute("currentUser"));
		repoUsuarios.inicializarIndicadoresYMetodologias(user);
		user.inicializarRepos();
		
		String nombreIndicador = request.queryParams("nombreIndicador");
		String expresionIndicador = request.queryParams("expresionIndicador");
		
		model.put("usuario", request.session().attribute("currentUser"));
		try {
			Indicador indicador = new Indicador(nombreIndicador, expresionIndicador, user);
			user.addIndicador(indicador);
			memCache.seCreoNuevoIndicador(indicador, repoEmpresas.getEmpresas(), user.getRepoIndicadores(),user);
			model.put("message", "Indicador \"" + nombreIndicador + "\" agregado con éxito.<br>Expresión: \"" + expresionIndicador + "\".");
			return Utils.render(model, "templates/crearIndicador.vm");
		} catch (SintaxisIncorrecta e1) {
			model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		} catch (ElementoYaExiste e2) {
			model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		}
	}

	
}
