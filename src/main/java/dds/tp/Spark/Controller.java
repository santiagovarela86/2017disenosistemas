package dds.tp.Spark;

import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;

public class Controller {

	public static Object index(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/index.vm");
	}
		
	public static Object pantallaPrincipal(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		//model.put("name", "Santiago");
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}
	
	public static Object visualizarCuentas(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		//model.put("name", "Santiago");
		return Utils.render(model, "templates/visualizarCuentas.vm");
	}
	
	public static Object crearIndicadores(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		//model.put("name", "Santiago");
		return Utils.render(model, "templates/crearIndicadores.vm");
	}
	
	public static Object evaluarIndicadores(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		//model.put("name", "Santiago");
		return Utils.render(model, "templates/evaluarIndicadores.vm");
	}
	
	public static Object evaluarMetodologias(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		//model.put("name", "Santiago");
		return Utils.render(model, "templates/evaluarMetodologias.vm");
	}

}
