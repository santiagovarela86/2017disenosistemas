package dds.tp.Spark;

import java.util.HashMap;
import java.util.Map;

import dds.tp.model.repositorios.RepositorioEmpresas;
import spark.Request;
import spark.Response;

public class VisualizarCuentasController {

	public static Object visualizarCuentas(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.cargarEmpresasGuardadas();
		//repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();
		
		//repoEmpresas.refrescarEmpresas();
		//repoEmpresas.refrescarBalances();	
		model.put("empresas", repoEmpresas.getEmpresas());
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/visualizarCuentas.vm");
	}
	
}
