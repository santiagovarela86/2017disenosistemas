package dds.tp.Spark;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.memcache.MemoriaCache;
import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioMetodologias;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class Controller {

	private static RepositorioUsuarios usuariosLogueado = new RepositorioUsuarios();
	private static RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
	private static MemoriaCache memCache = new MemoriaCache();
	
	public static Object mostrarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}
	
	public static Object redirectLogin(Request request, Response response) {
		response.redirect("/login");
		return null;
	}

	public static Object pantallaPrincipal(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}

	public static Object visualizarCuentas(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		repoEmpresas.refrescarEmpresas();
		repoEmpresas.refrescarBalances();	
		model.put("empresas", repoEmpresas.getEmpresas());
		return Utils.render(model, "templates/visualizarCuentas.vm");
	}

	public static Object crearIndicador(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		model.put("message", "");
		return Utils.render(model, "templates/crearIndicador.vm");
	}

	public static Object crearIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		Usuario user = usuariosLogueado.getUsuario(request.session().attribute("currentUser"));
		String nombreIndicador = request.queryParams("nombreIndicador");
		String expresionIndicador = request.queryParams("expresionIndicador");
		try {
			Indicador indicador = new Indicador(nombreIndicador, expresionIndicador, user);
			user.addIndicador(indicador);
			model.put("message", "Indicador agregado con éxito.<br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		} catch (SintaxisIncorrecta e1) {
			model.put("message", "Error en la expresión. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		} catch (ElementoYaExiste e2) {
			model.put("message", "Indicador existente. <br> Intente nuevamente. <br>");
			return Utils.render(model, "templates/crearIndicador.vm");
		}
	}

	public static Object evaluarIndicador(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		model.put("message", "");
		return Utils.render(model, "templates/evaluarIndicador.vm");
	}

	public static Object evaluarIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		Usuario user = usuariosLogueado.getUsuario(request.session().attribute("currentUser"));
		String nombreEmpresa = request.queryParams("nombreEmpresa");
		String nombreIndicador = request.queryParams("nombreIndicador");
		String periodo = request.queryParams("periodo");
		Double resultado;
		try {
			if(memCache.existePrecalculo(nombreIndicador, nombreEmpresa, periodo)) {
				resultado = memCache.getValorPrecalculado(nombreIndicador, nombreEmpresa, periodo);
				System.out.println("entro cache");
			}else {
				System.out.println("no entro cache");
				repoEmpresas.inicializarEmpresas();
				repoEmpresas.inicializarTodosLosbalances();	
				Empresa empresa = repoEmpresas.getEmpresa(nombreEmpresa);
				Balance balance = empresa.getBalance(periodo);
				Indicador indicador = user.getIndicador(nombreIndicador);
				resultado = indicador.evaluar(empresa, balance, user.getRepoIndicadores());
			}	
			String message = "Indicador: " + nombreIndicador + ", Empresa: " + nombreEmpresa + ", Período: "
					+ periodo + "<br>" + "Valor: " + resultado.toString() + ".";
			model.put("message", message);
			return Utils.render(model, "templates/evaluarIndicador.vm");
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		}
	}

	public static Object evaluarMetodologia(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		model.put("message", "");
		return Utils.render(model, "templates/evaluarMetodologia.vm");
	}

	public static Object evaluarMetodologiaEspecifica(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			model.put("message","");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		Usuario user = usuariosLogueado.getUsuario(request.session().attribute("currentUser"));
		Metodologia metodologia = new Metodologia();
		List<ResultadoAnalisis> resultados = new ArrayList<>();
		String nombreMetodologia = request.queryParams("nombreMetodologia");
		repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();
		try {
			metodologia = user.getMetodologia(nombreMetodologia);
			RepositorioMetodologias.inicializarCondiciones(metodologia);
			resultados = metodologia.evaluarEn(repoEmpresas.getEmpresas(), user.getRepoIndicadores());
			model.put("resultados", resultados);
			return Utils.render(model, "templates/evaluarMetodologiaResultados.vm");
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarMetodologia.vm");
		}
	}

	public static Object procesarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		String user = request.queryParams("user");
		String password = request.queryParams("password");
		if (UserController.autenticar(user, password)) {
			request.session().attribute("currentUser", request.queryParams("user"));
			Usuario usuario = UserController.buscarUsuario(user);
			usuario.inicializarRepos();
			usuariosLogueado.addUsuario(usuario);
			response.redirect("/pantallaPrincipal");
		} else {
			model.put("message", "Credenciales incorrectas.<br>Intente nuevamente.");
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		return null;
	}

	public static Object procesarLogout(Request request, Response response) {
		usuariosLogueado.removerUsuario(request.session().attribute("currentUser"));
		request.session().removeAttribute("currentUser");
		request.session().invalidate();
		response.redirect("/login");
		return null;
	}

	public static Boolean validarUsuarioLogueado(Request request, Response response) {
		return usuariosLogueado.contieneUsuario((String)request.session().attribute("currentUser"));
	}
}
