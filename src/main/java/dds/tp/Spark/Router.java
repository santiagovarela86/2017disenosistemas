package dds.tp.Spark;

import static spark.Spark.*;

public class Router {
	
	public static void start(){

		get("/index", Controller::index);
		
		get("/pantallaPrincipal", Controller::pantallaPrincipal);
		
		get("/visualizarCuentas", Controller::visualizarCuentas);
		
		get("/crearIndicadores", Controller::crearIndicadores);
		
		post("/crearIndicadores", Controller::crearIndicadorEspecifico);
		
		get("/evaluarIndicadores", Controller::evaluarIndicadores);
		
		post("/evaluarIndicadores", Controller::evaluarIndicadorEspecifico);
		
		get("/evaluarMetodologias", Controller::evaluarMetodologias);
	}

}
