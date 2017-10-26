package dds.tp.Spark;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Utils {

	public static String render(Map<String, Object> model, String templatePath){
		VelocityEngine ve = new VelocityEngine();
		
		//ESTO ES PARA QUE ME ENCUENTRE LOS TEMPLATES EN SRC/MAIN/RESOURCES
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template t = ve.getTemplate(templatePath, "UTF-8");
        
        VelocityContext context = new VelocityContext(model);
        
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
		return writer.toString();
	}

}
