package dds.tp.Spark;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import spark.Request;
import spark.Response;

public class Controller {
	
	public static Object pantallaLogin(Request request, Response response){
		VelocityEngine ve = new VelocityEngine();
		//ESTO ES PARA QUE ME ENCUENTRE LOS TEMPLATES EN SRC/MAIN/RESOURCES
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        
        Template t = ve.getTemplate("templates/pantallaPrincipal.vm");
        
        VelocityContext context = new VelocityContext();
        //context.put("name", "World");
        
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
		return writer.toString();
	}

}
