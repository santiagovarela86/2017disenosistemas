package dds.tp.Spark;

import static spark.Spark.*;

import dds.tp.batch.ListenerJobArchivosBatch;

public class Server {
	
	public static void main(String[] args) {
		
		port(getHerokuAssignedPort());
		
		staticFileLocation("/public");
		
		ListenerJobArchivosBatch listener = new ListenerJobArchivosBatch();
		listener.contextInitialized(null);
		
		Router.start();

	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
