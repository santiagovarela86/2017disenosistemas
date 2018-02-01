package dds.tp.batch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LectorConfiguraciones {
	
	public static String obtenerConfiguracion(String nombreConfiguracion){
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			input = new FileInputStream("config.properties");
			prop.load(input);
			return prop.getProperty(nombreConfiguracion);		

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}