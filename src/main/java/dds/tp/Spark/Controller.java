package dds.tp.Spark;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;
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
	
	public static Object crearIndicador(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		return Utils.render(model, "templates/crearIndicador.vm");
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
			return Utils.render(model, "templates/crearIndicador.vm");
		} 
		
		RepositorioIndicadores repositorio = new RepositorioIndicadores();
		repositorio.cargarIndicadoresGuardados();

		try {
			
			repositorio.addIndicador(indicador);

		} catch (ElementoYaExiste e2) {
			model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		}
		
		model.put("message", "Indicador agregado con éxito.<br>");
		return Utils.render(model, "templates/crearIndicador.vm");
	}
	
	public static Object evaluarIndicador(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		return Utils.render(model, "templates/evaluarIndicador.vm");
	}
	
	public static Object evaluarIndicadorEspecifico(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		
		Indicador indicador = new Indicador();
		Empresa empresa = new Empresa();
		Balance balance = null;
		
		String nombreEmpresa = request.queryParams("nombreEmpresa");
		String nombreIndicador = request.queryParams("nombreIndicador");
		String periodo = request.queryParams("periodo");
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.setEmpresas(repoEmpresas.cargarEmpresas()); 
		repoEmpresas.inicializarTodosLosbalances();
		
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarIndicadoresGuardados();
		
		try {
			indicador = repoIndicadores.getIndicador(nombreIndicador);
		}catch (ElementoNotFound e){
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		}
		
		try {
			empresa = repoEmpresas.getEmpresa(nombreEmpresa);
		}catch (ElementoNotFound e){
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		}
		
		try {
			balance = empresa.getBalance(periodo);
		}catch (ElementoNotFound e){
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		}
		
		Double resultado = indicador.evaluar(empresa, balance, repoIndicadores);
		
		String message = "Indicador: " 
							+ indicador.getNombre() 
								+ ", Empresa: " 
									+ empresa.getNombre()
										+ ", Período: "
											+ balance.getPeriodoNombre()
												+ "<br>"
													+ "Valor: " 
														+ resultado.toString() + ".";
		
		model.put("message", message);
		return Utils.render(model, "templates/evaluarIndicador.vm");
	}
	
	public static Object evaluarMetodologia(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		return Utils.render(model, "templates/evaluarMetodologia.vm");
	}
	
	public static Object evaluarMetodologiaEspecifica(Request request, Response response){
		Map<String, Object> model = new HashMap<>();
		Metodologia metodologia = new Metodologia();
		List<ResultadoAnalisis> resultados = new ArrayList<>();
		
		String nombreMetodologia = request.queryParams("nombreMetodologia");
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.setEmpresas(repoEmpresas.cargarEmpresas()); 
		repoEmpresas.inicializarTodosLosbalances();
		
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarIndicadoresGuardados();
		
		RepositorioMetodologias repoMetodologias = new RepositorioMetodologias();
		repoMetodologias.cargarMetodologiaGuardadas();
		
		try {
			metodologia = repoMetodologias.getMetodologia(nombreMetodologia);
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarMetodologia.vm");
		}
		
		resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		
		model.put("resultados", resultados);
		return Utils.render(model, "templates/evaluarMetodologiasResultados.vm");
		
	}
}
