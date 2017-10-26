package dds.tp.Spark;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import spark.Request;
import spark.Response;

public class Controller {

	public static Object index(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/index.vm");
	}
		
	public static Object pantallaPrincipal(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}
	
	public static Object visualizarCuentas(Request request, Response response){
		Map<String, Object> model = new HashMap<>();

		RepositorioEmpresas repositorio = new RepositorioEmpresas();
		repositorio.setEmpresas(repositorio.cargarEmpresas()); 
		repositorio.inicializarTodosLosbalances();
		List<Empresa> empresas = repositorio.getEmpresas();
		
		model.put("empresas", empresas);
		return Utils.render(model, "templates/visualizarCuentas.vm");
	}
	
	public static Object crearIndicadores(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		return Utils.render(model, "templates/crearIndicadores.vm");
	}
	
	public static Object crearIndicadorEspecifico(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		
		Indicador indicador;		
		String nombreIndicador = request.queryParams("nombreIndicador");
		String expresionIndicador = request.queryParams("expresionIndicador");
		
		try {
			
			indicador = new Indicador(nombreIndicador, expresionIndicador);

		} catch (SintaxisIncorrecta e1) {
			model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicadores.vm");
		} 
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		repositorio.cargarIndicadoresGuardados();

		try {
			
			repositorio.addIndicador(indicador);

		} catch (ElementoYaExiste e2) {
			model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicadores.vm");
		}
		
		model.put("message", "Indicador agregado con éxito.<br>");
		return Utils.render(model, "templates/crearIndicadores.vm");
	}
	
	public static Object evaluarIndicadores(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/evaluarIndicadores.vm");
	}
	
	public static Object evaluarMetodologias(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		return Utils.render(model, "templates/evaluarMetodologias.vm");
	}

}
