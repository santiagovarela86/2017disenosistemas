package dds.tp.SparkControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.tp.Spark.Utils;
import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class EvaluarMetodologiaController {

	public static Object evaluarMetodologia(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		String nombreUsuario = request.session().attribute("currentUser");
		
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.inicializar();
		Usuario user = repoUsuarios.getUsuario(nombreUsuario);
		repoUsuarios.inicializarIndicadoresYMetodologias(user);

		model.put("metodologias", user.getMetodologias());
		model.put("usuario", nombreUsuario);
		
		return Utils.render(model, "templates/evaluarMetodologia.vm");
	}

	public static Object evaluarMetodologiaEspecifica(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		String nombreUsuario = request.session().attribute("currentUser");
		String nombreMetodologia = request.queryParams("nombreMetodologia");
		
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.inicializar();
		Usuario user = repoUsuarios.getUsuario(nombreUsuario);
		repoUsuarios.inicializarIndicadoresYMetodologias(user);
		user.inicializarRepos();
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.cargarEmpresasGuardadas();
		repoEmpresas.inicializarTodosLosbalances();
		
		Metodologia metodologia = new Metodologia();
		List<ResultadoAnalisis> resultados = new ArrayList<>();
		
		model.put("metodologias", user.getMetodologias());
		model.put("usuario", nombreUsuario);
		
		try {
			metodologia = user.getMetodologia(nombreMetodologia);
			RepositorioMetodologias.inicializarCondiciones(metodologia);
			resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), user.getRepoIndicadores());
			model.put("resultados", resultados);
			return Utils.render(model, "templates/evaluarMetodologiaEspecifica.vm");
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarMetodologia.vm");
		}
	}
	
}
