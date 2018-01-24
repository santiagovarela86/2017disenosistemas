package dds.tp.Spark;

import static spark.Spark.*;

public class Router {
	
	public static void start(){
		
		get("/", Controller::redirectLogin);

		get("/login", Controller::mostrarLogin);
		
		post("/login", Controller::procesarLogin);
		
		get("/logout", Controller::procesarLogout);
		
		get("/pantallaPrincipal", Controller::pantallaPrincipal);
		
		get("/visualizarCuentas", Controller::visualizarCuentas);
		
		get("/indicador", Controller::crearIndicador);
		
		post("/indicador", Controller::crearIndicadorEspecifico);
		
		get("/evaluarIndicador", Controller::evaluarIndicador);
		
		get("/evaluarIndicadorEspecifico", Controller::evaluarIndicadorEspecifico);
		
		get("/evaluarMetodologia", Controller::evaluarMetodologia);
		
		get("/evaluarMetodologiaEspecifica", Controller::evaluarMetodologiaEspecifica);
	}

}
