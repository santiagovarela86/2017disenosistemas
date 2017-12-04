package dds.tp.Spark;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;

import dds.tp.Spark.util.ViewUtil;
import dds.tp.Spark.util.Path;
import dds.tp.memcache.MemoriaCache;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioUsuario;

public class Router {

	public static RepositorioUsuario repositorioUsuario;
	public static RepositorioEmpresas repoEmpresas;
	public static MemoriaCache memCache;

	public static void start() {

		repositorioUsuario = new RepositorioUsuario();
		repoEmpresas = new RepositorioEmpresas();
		memCache = new MemoriaCache();
		
		
		
		// CONTROLADOR: LOGIN
		get(Path.Web.LOGIN, LoginController.serveLoginPage);
		post(Path.Web.PANTALLA_PRINCIPAL, LoginController.handleLoginPost);
		post(Path.Web.LOGOUT, LoginController.handleLogoutPost);

		// CONTROLADOR: Visualizar Cuentas
		get(Path.Web.VISUALIZAR_CUENTAS, VisualizarCuentasController.visualizarCuentas);

		// CONTROLADOR: Crear Indicadores
		get(Path.Web.CREAR_INDICADOR, CrearIndicadorController.crearIndicador);
		post(Path.Web.CREAR_INDICADOR, CrearIndicadorController.crearIndicadorEspecifico);

		// CONTROLADOR: Evaluar Indicadores
		get(Path.Web.EVALUAR_INDICADOR, EvaluarIndicadorController.evaluarIndicador);
		get(Path.Web.EVALUAR_INDICADOR_INGRESADO, EvaluarIndicadorController.evaluarIndicadorEspecifico);

		// CONTROLADOR: Evaluar Metodologias
		get(Path.Web.EVALUAR_METODOLOGIA, EvaluarMetodologiaController.evaluarMetodologia);
		get(Path.Web.EVALUAR_METODOLOGIA_INGRESADA, EvaluarMetodologiaController.evaluarMetodologiaEspecifica);

		get("*",                     ViewUtil.notFound);

	}

}
