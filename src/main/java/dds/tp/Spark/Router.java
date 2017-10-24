package dds.tp.Spark;

import static spark.Spark.*;

public class Router {
	
	public static void start(){

		get("/index", Controller::pantallaLogin);
		
		get("/pantallaPrincipal", Controller::pantallaPrincipal);

	}

}
