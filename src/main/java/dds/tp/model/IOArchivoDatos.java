package dds.tp.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import dds.tp.model.Cuenta;

public class IOArchivoDatos {
	
	private String path;
	
	public IOArchivoDatos(String fileName){
		this.path = fileName;
	}
	
	//Cambio el nomnre ya que carga empresas semestres cuentas
	public List<Empresa> obtenerDatos(Stream<String> lineas){
		ArrayList<Empresa> empresas = new ArrayList<>();
		
		List<String> list = new ArrayList<String>();
		list = lineas.collect(Collectors.toList());
		
		for (String s : list) {
			Supplier<Stream<String>> streamSupplier = () -> Stream.of(s);
			streamSupplier.get().forEach(line -> convertAndAddCuenta(line,empresas));
		}
		return empresas;
	}

	private void convertAndAddCuenta(String line,List<Empresa> empresas) {

		List<String> string = Stream.of(line).map(w -> w.split(",")).flatMap(Arrays::stream).distinct()
				.collect(Collectors.toList());

		String nombre = string.get(0).trim();
		String empresa = string.get(1).trim();
		String anio = string.get(2).trim();
		Float valor = Float.parseFloat(string.get(3).trim());
		
		Empresa empr;
		try {
			empr = empresas.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(empresa)).collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			empr = new Empresa(empresa);
			empresas.add(empr);
		}
		Balance bal;
		try {
			bal = empr.getBalances().stream().filter(elem -> elem.getPeriodo().equalsIgnoreCase(anio)).collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			bal = new Balance(anio);
			empr.getBalances().add(bal);
		}
		Cuenta cta;
		try {
			cta = bal.getCuentas().stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			cta = new Cuenta(nombre,valor);
			bal.addCuenta(cta);
		}
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
