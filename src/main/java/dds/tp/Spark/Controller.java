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
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioMetodologias;
import spark.Request;
import spark.Response;

public class Controller {

	private static Usuario usuarioLogueado;
	private static RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
	
	public static Object mostrarLogin(Request request, Response response) {
		if (validarUsuarioLogueadoPantallaLogin(request, response)) {
			response.redirect("/pantallaPrincipal");
			return null;
		} else {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
	}
	
	public static Object redirectLogin(Request request, Response response) {
		response.redirect("/login");
		return null;
	}

	public static Object pantallaPrincipal(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			return Utils.render(model, "templates/pantallaPrincipal.vm");
		} else
			return null;
	}

	public static Object visualizarCuentas(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			repoEmpresas.inicializarEmpresas();
			repoEmpresas.inicializarTodosLosbalances();
			model.put("empresas", repoEmpresas.getEmpresas());
			return Utils.render(model, "templates/visualizarCuentas.vm");
		} else
			return null;
	}

	public static Object crearIndicador(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "");
			return Utils.render(model, "templates/crearIndicador.vm");
		} else
			return null;
	}

	public static Object crearIndicadorEspecifico(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			String nombreIndicador = request.queryParams("nombreIndicador");
			String expresionIndicador = request.queryParams("expresionIndicador");
			try {
				Indicador indicador = new Indicador(nombreIndicador, expresionIndicador, usuarioLogueado);
				usuarioLogueado.addIndicador(indicador);
				model.put("message", "Indicador agregado con éxito.<br>");
				return Utils.render(model, "templates/crearIndicador.vm");
			} catch (SintaxisIncorrecta e1) {
				model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
				return Utils.render(model, "templates/crearIndicador.vm");
			} catch (ElementoYaExiste e2) {
				model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
				return Utils.render(model, "templates/crearIndicador.vm");
			}
		} else
			return null;
	}

	public static Object evaluarIndicador(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		} else
			return null;
	}

	public static Object evaluarIndicadorEspecifico(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			String nombreEmpresa = request.queryParams("nombreEmpresa");
			String nombreIndicador = request.queryParams("nombreIndicador");
			String periodo = request.queryParams("periodo");
			//Esto se puede poner como usuario logueado
			repoEmpresas.inicializarEmpresas();
			repoEmpresas.inicializarTodosLosbalances();	
			try {
				Empresa empresa = repoEmpresas.getEmpresa(nombreEmpresa);
				Balance balance = empresa.getBalance(periodo);
				Indicador indicador = usuarioLogueado.getIndicador(nombreIndicador);
				Double resultado = indicador.evaluar(empresa, balance, usuarioLogueado.getRepoIndicadores());
				String message = "Indicador: " + indicador.getNombre() + ", Empresa: " + empresa.getNombre() + ", Período: "
						+ balance.getPeriodoNombre() + "<br>" + "Valor: " + resultado.toString() + ".";

				model.put("message", message);
				return Utils.render(model, "templates/evaluarIndicador.vm");
			} catch (ElementoNotFound e) {
				model.put("message", e.getMessage() + ".");
				return Utils.render(model, "templates/evaluarIndicador.vm");
			}
		} else
			return null;
	}

	public static Object evaluarMetodologia(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "");
			return Utils.render(model, "templates/evaluarMetodologia.vm");
		} else
			return null;
	}

	public static Object evaluarMetodologiaEspecifica(Request request, Response response) {
		if (validarUsuarioLogueado(request, response)) {
			Map<String, Object> model = new HashMap<>();
			Metodologia metodologia = new Metodologia();
			List<ResultadoAnalisis> resultados = new ArrayList<>();
			String nombreMetodologia = request.queryParams("nombreMetodologia");
			repoEmpresas.inicializarEmpresas();
			repoEmpresas.inicializarTodosLosbalances();
			try {
				metodologia = usuarioLogueado.getMetodologia(nombreMetodologia);
				RepositorioMetodologias.inicializarCondiciones(metodologia);
				resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), usuarioLogueado.getRepoIndicadores());
				model.put("resultados", resultados);
				return Utils.render(model, "templates/evaluarMetodologiaResultados.vm");
			} catch (ElementoNotFound e) {
				model.put("message", e.getMessage() + ".");
				return Utils.render(model, "templates/evaluarMetodologia.vm");
			}
		} else
			return null;
	}

	public static Object procesarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		String user = request.queryParams("user");
		String password = request.queryParams("password");
		if (UserController.autenticar(user, password)) {
			request.session().attribute("currentUser", request.queryParams("user"));
			usuarioLogueado = UserController.buscarUsuario(user);
			usuarioLogueado.inicializarRepos();
			response.redirect("/pantallaPrincipal");
		} else {
			model.put("message", "Credenciales incorrectas.<br>Intente nuevamente.");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		return null;
	}

	public static Object procesarLogout(Request request, Response response) {
		request.session().removeAttribute("currentUser");
		request.session().invalidate();
		response.redirect("/login");
		return null;
	}

	public static Boolean validarUsuarioLogueado(Request request, Response response) {
		if (request.session().attribute("currentUser") == null) {
			response.redirect("/login");
			return false;
		} else
			return true;
	}

	public static Boolean validarUsuarioLogueadoPantallaLogin(Request request, Response response) {
		if (request.session().attribute("currentUser") != null) {
			return true;
		} else
			return false;
	}

}
