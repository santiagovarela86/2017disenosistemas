package dds.tp.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Cuenta;

public class LectorCuentas {
	
	private String path;
	
	public LectorCuentas(String fileName){
		this.path = fileName;
	}
	
	public List<Empresa> obtenerDatos(Stream<String> lineas){
		ArrayList<Empresa> empresas = new ArrayList<>();
		lineas.forEach(line -> convertAndAddCuenta(line,empresas));
		return empresas;
	}

	private void convertAndAddCuenta(String line,List<Empresa> empresas) {

		List<String> string = Stream.of(line)
				.map(w -> w.split(","))
				.flatMap(Arrays::stream)
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
			try {
				bal.addCuenta(cta);
			} catch (ElementoYaExiste e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
