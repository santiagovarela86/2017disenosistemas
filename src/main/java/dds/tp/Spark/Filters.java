package dds.tp.Spark;

import spark.Filter;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Filters {

	
	public static Filter urlPattern = (Request request, Response response) -> {
		halt(401, "Go Away!");
    };
	
}
