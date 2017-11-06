package dds.tp.Spark;

import static spark.Spark.*;

public class Server {
	
	public static void main(String[] args) {
		
		port(90);
		
		staticFileLocation("/public");
		
		Router.start();
		
	}
}
