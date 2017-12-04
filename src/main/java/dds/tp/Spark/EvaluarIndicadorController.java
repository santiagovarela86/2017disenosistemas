package dds.tp.Spark;

import static dds.tp.Spark.Router.memCache;
import static dds.tp.Spark.Router.repoEmpresas;
import java.util.HashMap;
import java.util.Map;
import dds.tp.Spark.util.Path;
import dds.tp.Spark.util.ViewUtil;
import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import spark.Request;
import spark.Response;
import spark.Route;

public class EvaluarIndicadorController {

	public static Route evaluarIndicador= (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		model.put("message", "");
		model.put("usuario", request.session().attribute("usuario"));
		return ViewUtil.render(request, model, Path.Template.EVALUAR_INDICADOR);
	};

	public static Route evaluarIndicadorEspecifico= (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		Usuario usuario = request.session().attribute("usuario");
		
		String nombreEmpresa = request.queryParams("nombreEmpresa");
		String nombreIndicador = request.queryParams("nombreIndicador");
		String periodo = request.queryParams("periodo");
		Double resultado;
		model.put("usuario",usuario);
		try {
			if(memCache.existePrecalculo(nombreIndicador, nombreEmpresa, periodo,usuario)) {
				resultado = memCache.getValorPrecalculado(nombreIndicador, nombreEmpresa, periodo,usuario);
				System.out.println("entro cache");
			}else {
			//	usuariosLogueado.inicializarIndicadoresYMetodologias(user);
				usuario.inicializarRepos();
				System.out.println("no entro cache");
				repoEmpresas.inicializarEmpresas();
				repoEmpresas.inicializarTodosLosbalances();	
				Empresa empresa = repoEmpresas.getEmpresa(nombreEmpresa);
				Balance balance = empresa.getBalance(periodo);
				Indicador indicador = usuario.getIndicador(nombreIndicador);
				resultado = indicador.evaluar(empresa, balance, usuario.getRepoIndicadores());
			}	
			String message = "Indicador: " + nombreIndicador
						+ "<br>Empresa: " + nombreEmpresa 
						+ "<br>Período: " + periodo 
						+ "<br>Valor: " + resultado.toString();
			model.put("message", message);
			return ViewUtil.render(request, model, Path.Template.EVALUAR_INDICADOR);
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return ViewUtil.render(request, model, Path.Template.EVALUAR_INDICADOR);
		}
	};
	
}
