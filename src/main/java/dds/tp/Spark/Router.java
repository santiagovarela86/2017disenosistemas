package dds.tp.Spark;

import static spark.Spark.*;

import dds.tp.SparkControllers.CrearIndicadorController;
import dds.tp.SparkControllers.EvaluarIndicadorController;
import dds.tp.SparkControllers.EvaluarMetodologiaController;
import dds.tp.SparkControllers.LoginController;
import dds.tp.SparkControllers.PantallaPrincipalController;
import dds.tp.SparkControllers.SessionController;
import dds.tp.SparkControllers.VisualizarCuentasController;

public class Router {
	
	public static void start(){
		
		before("/stockApp/*", SessionController::validarUsuarioLogueado);
		
		get("/", LoginController::redirectLogin);

		get("/login", LoginController::mostrarLogin);
		
		post("/login", LoginController::procesarLogin);
		
		get("/logout", LoginController::procesarLogout);
		
		get("/stockApp/pantallaPrincipal", PantallaPrincipalController::pantallaPrincipal);
		
		get("/stockApp/visualizarCuentas", VisualizarCuentasController::visualizarCuentas);
		
		get("/stockApp/indicador", CrearIndicadorController::crearIndicador);
		
		post("/stockApp/indicador", CrearIndicadorController::crearIndicadorEspecifico);
		
		get("/stockApp/evaluarIndicador", EvaluarIndicadorController::evaluarIndicador);
		
		get("/stockApp/evaluarIndicadorEspecifico", EvaluarIndicadorController::evaluarIndicadorEspecifico);
		
		get("/stockApp/evaluarMetodologia", EvaluarMetodologiaController::evaluarMetodologia);
		
		get("/stockApp/evaluarMetodologiaEspecifica", EvaluarMetodologiaController::evaluarMetodologiaEspecifica);
		
	}

}
