package dds.tp.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;

import dds.tp.model.Cuenta;

public class IOArchivoCuentas {
	
	private String path;
	private Stream<String> lines;
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	public IOArchivoCuentas(String _fileName){
		path = _fileName;
	}
	
	public void cargarCuentas(){
		try {
			lines = Files.lines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		lines.forEach(line->cuentas.add(transformarACuenta(line)));
	}

	private Cuenta transformarACuenta(String line){
		String[] tokens = line.split(",");

		String nombre = tokens[0].trim();
		String empresa = tokens[1].trim();
		Integer anio = Integer.parseInt(tokens[2].trim());
		Float valor = Float.parseFloat(tokens[3].trim());
		
		return new Cuenta(nombre, empresa, anio, valor);
	}
	
	public void consultarCuentas(){
		cuentas.forEach(cuenta->System.out.println(transformoALinea(cuenta)));
	}
	
	private String transformoALinea(Cuenta cuenta){
		return cuenta.toString();
	}
	
	public String getPath(){
		return path;
	}
	
	public ArrayList<Cuenta> getCuentas(){
		return cuentas;
	}
	
}
