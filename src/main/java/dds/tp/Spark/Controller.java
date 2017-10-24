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

}
