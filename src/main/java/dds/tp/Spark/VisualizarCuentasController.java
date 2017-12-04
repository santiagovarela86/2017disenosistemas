package dds.tp.Spark;

import static dds.tp.Spark.Router.repoEmpresas;

import java.util.HashMap;
import java.util.Map;

import dds.tp.Spark.util.Path;
import dds.tp.Spark.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class VisualizarCuentasController {

	
	public static Route visualizarCuentas= (Request request, Response response) ->{
		
		LoginController.ensureUserIsLoggedIn(request, response);
		
		Map<String, Object> model = new HashMap<>();
		repoEmpresas.refrescarEmpresas();
		repoEmpresas.refrescarBalances();	
		model.put("empresas", repoEmpresas.getEmpresas());
		model.put("usuario", request.session().attribute("usuario"));
		return ViewUtil.render(request, model, Path.Template.VISUALIZAR_CUENTAS);
	};
	
}
