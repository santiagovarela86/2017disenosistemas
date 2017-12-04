package dds.tp.Spark;

import static dds.tp.Spark.Router.repoEmpresas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.tp.Spark.util.Path;
import dds.tp.Spark.util.ViewUtil;
import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioMetodologias;
import spark.Request;
import spark.Response;
import spark.Route;

public class EvaluarMetodologiaController {

	
	public static Route evaluarMetodologia= (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		model.put("usuario", request.session().attribute("usuario"));
		return ViewUtil.render(request, model, Path.Template.EVALUAR_METODOLOGIA);
	};

	public static Route evaluarMetodologiaEspecifica= (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		Usuario usuario = request.session().attribute("usuario");
		
		usuario.inicializarRepos();
		Metodologia metodologia = new Metodologia();
		List<ResultadoAnalisis> resultados = new ArrayList<>();
		String nombreMetodologia = request.queryParams("nombreMetodologia");
		repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();
		model.put("usuario", request.session().attribute("usuario"));
		try {
			metodologia = usuario.getMetodologia(nombreMetodologia);
			RepositorioMetodologias.inicializarCondiciones(metodologia);
			resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), usuario.getRepoIndicadores());
			model.put("resultados", resultados);
			return ViewUtil.render(request, model, Path.Template.EVALUAR_METODOLOGIA_INGRESADA);
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return ViewUtil.render(request, model, Path.Template.EVALUAR_METODOLOGIA);
		}
	};

	
	
}
