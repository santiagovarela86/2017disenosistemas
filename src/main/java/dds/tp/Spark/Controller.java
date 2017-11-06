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
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class Controller {

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
			RepositorioEmpresas repositorio = new RepositorioEmpresas();

			repositorio.setEmpresas(repositorio.cargarEmpresas());
			repositorio.inicializarTodosLosbalances();
			List<Empresa> empresas = repositorio.getEmpresas();

			model.put("empresas", empresas);
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

			Indicador indicador;
			String nombreIndicador = request.queryParams("nombreIndicador");
			String expresionIndicador = request.queryParams("expresionIndicador");
			String nombreUsuario = request.session().attribute("currentUser");
			
			RepositorioUsuarios repoUsuarios = new RepositorioUsuarios().obtenerRepoCompleto();
			Usuario usuario = repoUsuarios.getUsuario(nombreUsuario);

			try {

				indicador = new Indicador(nombreIndicador, expresionIndicador, usuario);

			} catch (SintaxisIncorrecta e1) {
				model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
				return Utils.render(model, "templates/crearIndicador.vm");
			}
			
			//RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
			//repoIndicadores.cargarIndicadoresDelUsuario(usuario);

			try {
				
				usuario.addIndicador(indicador);
				repoUsuarios.actualizarUsuario(usuario);

			} catch (ElementoYaExiste e2) {
				model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
				return Utils.render(model, "templates/crearIndicador.vm");
			}

			model.put("message", "Indicador agregado con éxito.<br>");
			return Utils.render(model, "templates/crearIndicador.vm");
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
			Indicador indicador = new Indicador();
			Empresa empresa = new Empresa();
			Balance balance = null;

			String nombreEmpresa = request.queryParams("nombreEmpresa");
			String nombreIndicador = request.queryParams("nombreIndicador");
			String periodo = request.queryParams("periodo");
			String nombreUsuario = request.session().attribute("currentUser");

			RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
			repoEmpresas.setEmpresas(repoEmpresas.cargarEmpresas());
			repoEmpresas.inicializarTodosLosbalances();

			RepositorioUsuarios repoUsuarios = new RepositorioUsuarios().obtenerRepoCompleto();
			Usuario usuario = repoUsuarios.getUsuario(nombreUsuario);
			Usuario usuarioDefault = repoUsuarios.getUsuario("default");
			
			RepositorioIndicadores repoIndicadores = new RepositorioIndicadores().obtenerRepositorioCompleto();		

			try {
				//BUSCO PRIMERO EN LOS INDICADORES PUBLICOS
				indicador = repoIndicadores.getIndicador(nombreIndicador, usuarioDefault.getNombre());				
			} catch (ElementoNotFound e) {
				//SI LLEGA ACA ES PORQUE NO EXISTE EL INDICADOR PUBLICO
				try {
					indicador = usuario.getIndicador(nombreIndicador);
				} catch (ElementoNotFound e2) {
					model.put("message", e2.getMessage() + ".");
					return Utils.render(model, "templates/evaluarIndicador.vm");
				}
			}

			try {
				empresa = repoEmpresas.getEmpresa(nombreEmpresa);
			} catch (ElementoNotFound e) {
				model.put("message", e.getMessage() + ".");
				return Utils.render(model, "templates/evaluarIndicador.vm");
			}

			try {
				balance = empresa.getBalance(periodo);
			} catch (ElementoNotFound e) {
				model.put("message", e.getMessage() + ".");
				return Utils.render(model, "templates/evaluarIndicador.vm");
			}

			RepositorioIndicadores repoIndicadoresUtilizablesPorElUsuario = new RepositorioIndicadores();
			repoIndicadoresUtilizablesPorElUsuario.setIndicadores(usuario.getIndicadores());
			RepositorioIndicadores repoIndicadoresPublicos = new RepositorioIndicadores().obtenerIndicadoresPublicosGuardados();
			repoIndicadoresUtilizablesPorElUsuario.addIndicadores(repoIndicadoresPublicos.getIndicadores());

			Double resultado;

			try {
				resultado = indicador.evaluar(empresa, balance, repoIndicadoresUtilizablesPorElUsuario);
			} catch (ElementoNotFound e){
				model.put("message", e.getMessage() + ".");
				return Utils.render(model, "templates/evaluarIndicador.vm");
			}

			String message = "Indicador: " + indicador.getNombre() + ", Empresa: " + empresa.getNombre() + ", Período: "
					+ balance.getPeriodoNombre() + "<br>" + "Valor: " + resultado.toString() + ".";

			model.put("message", message);
			return Utils.render(model, "templates/evaluarIndicador.vm");
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

			repoMetodologias.inicializarCondiciones(metodologia);
			resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);

			model.put("resultados", resultados);
			return Utils.render(model, "templates/evaluarMetodologiaResultados.vm");
		} else
			return null;
	}

	public static Object procesarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		String user = request.queryParams("user");
		String password = request.queryParams("password");

		if (UserController.autenticar(user, password)) {
			request.session().attribute("currentUser", request.queryParams("user"));
			response.redirect("/pantallaPrincipal");
		} else {
			model.put("message", "Credenciales incorrectas.<br>Intente nuevamente.");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}

		return null;
	}

	public static Object procesarLogout(Request request, Response response) {
		request.session().removeAttribute("currentUser");
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
