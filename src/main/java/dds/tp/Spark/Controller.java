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
import dds.tp.model.ResultadoIndicadores;
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

	private static RepositorioUsuarios usuariosLogueado = new RepositorioUsuarios();
	private static RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
	private static MemoriaCache memCache = new MemoriaCache();
	
	public static Object mostrarLogin(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}
	
	public static Object redirectLogin(Request request, Response response) {
		response.redirect("/login");
		return null;
	}

	public static Object pantallaPrincipal(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/pantallaPrincipal.vm");
	}

	public static Object visualizarCuentas(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		repoEmpresas.refrescarEmpresas();
		repoEmpresas.refrescarBalances();	
		model.put("empresas", repoEmpresas.getEmpresas());
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/visualizarCuentas.vm");
	}

	public static Object crearIndicador(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/crearIndicador.vm");
	}

	public static Object crearIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		Usuario user = usuariosLogueado.getUsuario(request.session().attribute("currentUser"));
		usuariosLogueado.inicializarIndicadoresYMetodologias(user);
		user.inicializarRepos();
		String nombreIndicador = request.queryParams("nombreIndicador");
		String expresionIndicador = request.queryParams("expresionIndicador");
		model.put("usuario", request.session().attribute("currentUser"));
		try {
			Indicador indicador = new Indicador(nombreIndicador, expresionIndicador, user);
			user.addIndicador(indicador);
			memCache.seCreoNuevoIndicador(indicador, repoEmpresas.getEmpresas(), user.getRepoIndicadores(),user);
			model.put("message", "Indicador \"" + nombreIndicador + "\" agregado con éxito.<br>Expresión: \"" + expresionIndicador + "\".");
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
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		
		model.put("usuario", request.session().attribute("currentUser"));
		
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		List<Indicador> indicadores = repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.inicializar().getUsuario(request.session().attribute("currentUser")));
		indicadores.addAll(repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.getUsuario("default")));

		model.put("indicadores", indicadores);
		model.put("empresas", repoEmpresas.cargarEmpresas());
		
		return Utils.render(model, "templates/evaluarIndicador.vm");
	}

	public static Object evaluarIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		
		String nombreUsuario = request.session().attribute("currentUser");
		model.put("usuario", nombreUsuario);	

		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		List<Indicador> indicadores = repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.inicializar().getUsuario(nombreUsuario));
		indicadores.addAll(repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.getUsuario("default")));

		model.put("indicadores", indicadores);
		model.put("empresas", repoEmpresas.cargarEmpresas());
		
		String nombreEmpresa = request.queryParams("selectEmpresa");
		String nombreIndicador = request.queryParams("selectIndicador");
		
		model.put("nombreEmpresa", nombreEmpresa);
		model.put("nombreIndicador", nombreIndicador);
		
		List<ResultadoIndicadores> resultSet = new ArrayList<ResultadoIndicadores>();
		
		repoEmpresas.cargarEmpresasGuardadas();
		repoIndicadores.cargarIndicadoresGuardados();
		Usuario user = repoUsuarios.getUsuario(nombreUsuario);
		Empresa empresa = repoEmpresas.getEmpresa(nombreEmpresa);
		repoUsuarios.inicializarIndicadoresYMetodologias(user);
		repoEmpresas.inicializarBalances(empresa);
		user.inicializarRepos();
		
		Indicador indicador;
		
		if (user.tieneIndicador(nombreIndicador)){
			indicador = user.getIndicador(nombreIndicador);
		} else {
			indicador = repoIndicadores.getIndicador(nombreIndicador);			
		}
		
		if (empresa.getTodosLosBalances().stream().anyMatch(balance -> indicador.puedeEvaluar(balance, repoIndicadores))){
			
			empresa.getTodosLosBalances().forEach(balance -> {
				ResultadoIndicadores resultado = new ResultadoIndicadores();
				resultado.setPeriodo(balance.getPeriodoNombre());
				resultado.setValor(indicador.evaluar(empresa, balance, user.getRepoIndicadores()).toString());
				resultSet.add(resultado);
			});
			
		} else {
			model.put("message", "No se puede evaluar el indicador \"" +
					nombreIndicador + "\" en \"" + nombreEmpresa + 
						"\";<br> no tiene las cuentas necesarias para calcularlo.");
		}
		
		model.put("resultSet", resultSet);
		
		return Utils.render(model, "templates/evaluarIndicadorEspecifico.vm");
		
		/*
		try {
			if(memCache.existePrecalculo(nombreIndicador, nombreEmpresa, periodo,user)) {
				resultado = memCache.getValorPrecalculado(nombreIndicador, nombreEmpresa, periodo,user);
				System.out.println("entro cache");
			}else {
				usuariosLogueado.inicializarIndicadoresYMetodologias(user);
				user.inicializarRepos();
				System.out.println("no entro cache");
				repoEmpresas.inicializarEmpresas();
				repoEmpresas.inicializarTodosLosbalances();	
				Empresa empresa = repoEmpresas.getEmpresa(nombreEmpresa);
				Balance balance = empresa.getBalance(periodo);
				Indicador indicador = user.getIndicador(nombreIndicador);
				resultado = indicador.evaluar(empresa, balance, user.getRepoIndicadores());
			}	
			String message = "Indicador: " + nombreIndicador
						+ "<br>Empresa: " + nombreEmpresa 
						+ "<br>Período: " + periodo 
						+ "<br>Valor: " + resultado.toString();
			model.put("message", message);
			return Utils.render(model, "templates/evaluarIndicador.vm");
		} catch (ElementoNotFound e) {
			model.put("message", e.getMessage() + ".");
			return Utils.render(model, "templates/evaluarIndicador.vm");
		}
		*/
	}

	public static Object evaluarMetodologia(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		
		model.put("usuario", request.session().attribute("currentUser"));
		return Utils.render(model, "templates/evaluarMetodologia.vm");
	}

	public static Object evaluarMetodologiaEspecifica(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		if(!validarUsuarioLogueado(request, response)){
			
			return Utils.render(model, "templates/mostrarLogin.vm");
		}
		Usuario user = usuariosLogueado.getUsuario(request.session().attribute("currentUser"));
		usuariosLogueado.inicializarIndicadoresYMetodologias(user);
		user.inicializarRepos();
		Metodologia metodologia = new Metodologia();
		List<ResultadoAnalisis> resultados = new ArrayList<>();
		String nombreMetodologia = request.queryParams("nombreMetodologia");
		repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();
		model.put("usuario", request.session().attribute("currentUser"));
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
