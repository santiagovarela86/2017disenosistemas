package dds.tp.SparkControllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.tp.Spark.Utils;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.ResultadoIndicadores;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuarios;
import spark.Request;
import spark.Response;

public class EvaluarIndicadorController {
	
	public static Object evaluarIndicador(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		String usuario = request.session().attribute("currentUser");
		
		model.put("usuario", usuario);
		
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		List<Indicador> indicadores = repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.inicializar().getUsuario(usuario));
		indicadores.addAll(repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.getUsuario("default")));

		model.put("indicadores", indicadores);
		model.put("empresas", repoEmpresas.cargarEmpresas());
		
		return Utils.render(model, "templates/evaluarIndicador.vm");
	}

	public static Object evaluarIndicadorEspecifico(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		
		String nombreUsuario = request.session().attribute("currentUser");
		model.put("usuario", nombreUsuario);	

		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		
		List<Indicador> indicadoresQueElUsuarioPuedeUsar = repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.inicializar().getUsuario(nombreUsuario));
		indicadoresQueElUsuarioPuedeUsar.addAll(repoIndicadores.cargarIndicadoresPorUsuario(repoUsuarios.getUsuario("default")));

		model.put("indicadores", indicadoresQueElUsuarioPuedeUsar);
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

}
