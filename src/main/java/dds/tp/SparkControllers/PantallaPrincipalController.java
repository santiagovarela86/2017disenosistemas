package dds.tp.SparkControllers;

import java.util.HashMap;
import java.util.Map;

import dds.tp.Spark.Utils;
import dds.tp.model.repositorios.RepositorioEmpresas;
import spark.Request;
import spark.Response;

public class PantallaPrincipalController {

	public static Object pantallaPrincipal(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}
	
}
