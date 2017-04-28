package dds.tp.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import dds.tp.model.Cuenta;

public class IOArchivoCuentas {
	
	private String path;
	private Stream<String> lines;
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	private Empresa empresa;
	
	public IOArchivoCuentas(String _fileName){
		path = _fileName;
		empresa = new Empresa();
	}
	
	public void cargarCuentas() {
		try {

			lines = Files.lines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Para que no se carguen varias veces los mismos datos
		cuentas.clear();

		List<String> list = new ArrayList<String>();
		list = lines.collect(Collectors.toList());

		for (String s : list) {
			Supplier<Stream<String>> streamSupplier = () -> Stream.of(s);
			streamSupplier.get().forEach(line -> cuentas.add(transformarACuenta(line)));
			
			// Realiza la carga de cuentas a la clase Empresa, esto sirve
			// para realizar las CONSULTAS(Busqueda)
			streamSupplier.get().forEach(line -> empresa.setCuenta(transformarACuenta(line)));
		}
		
	}
	
	/*
	public void cargarCuentas(){
		Stream<String> lines = null;
		
			try {
				lines = Files.lines(Paths.get(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			cuentas.clear();
			lines.forEach(line->cuentas.add(transformarACuenta(line)));
	}
	*/

	private Cuenta transformarACuenta(String line) {

		List<String> string = Stream.of(line).map(w -> w.split(",")).flatMap(Arrays::stream).distinct()
				.collect(Collectors.toList());

		String nombre = string.get(0).trim();
		String empresa = string.get(1).trim();
		Integer anio = Integer.parseInt(string.get(2).trim());
		Float valor = Float.parseFloat(string.get(3).trim());

		return new Cuenta(nombre, empresa, anio, valor);
	}

	/*
	private Cuenta transformarACuenta(String line){
		String[] tokens = line.split(",");

		String nombre = tokens[0].trim();
		String empresa = tokens[1].trim();
		Integer anio = Integer.parseInt(tokens[2].trim());
		Float valor = Float.parseFloat(tokens[3].trim());
		
		return new Cuenta(nombre, empresa, anio, valor);
	}*/
	
	/* Legacy
	public void consultarCuentas(){
		cuentas.forEach(cuenta->System.out.println(transformoALinea(cuenta)));
	}

	private String transformoALinea(Cuenta cuenta){
		return cuenta.toString();
	}*/
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public ArrayList<Cuenta> getCuentas(){
		return cuentas;
	}
	
}
