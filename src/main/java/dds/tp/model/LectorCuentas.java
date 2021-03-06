package dds.tp.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Cuenta;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Semestral;
import dds.tp.model.repositorios.RepositorioEmpresas;

public class LectorCuentas {
	
	private String path;
	
	public LectorCuentas(String fileName){
		this.path = fileName;
	}
	
	public List<Empresa> obtenerDatos(Stream<String> lineas){
		RepositorioEmpresas repoEmpresaTemporal = new RepositorioEmpresas();
		lineas.forEach(line -> convertAndAddCuenta(line,repoEmpresaTemporal));
		return repoEmpresaTemporal.getEmpresas();
	}
	
	public List<Empresa> obtenerDatos(List<String> lineas){
		RepositorioEmpresas repoEmpresaTemporal = new RepositorioEmpresas();
		lineas.forEach(line -> convertAndAddCuenta(line,repoEmpresaTemporal));
		return repoEmpresaTemporal.getEmpresas();
	}

	public void convertAndAddCuenta(String line,RepositorioEmpresas empresas) {

		List<String> string = Stream.of(line)
				.map(w -> w.split(","))
				.flatMap(Arrays::stream)
				.collect(Collectors.toList());

		String nombre = string.get(0).trim();
		String empresaNombre = string.get(1).trim();
		String periodo = string.get(2).trim();
		Double valor = Double.parseDouble(string.get(3).trim());
		Integer anioFundacion = Integer.parseInt(string.get(4).trim());
	    
		Balance bal;
		Empresa empresa = new Empresa(empresaNombre,anioFundacion);
		if(periodo.length()>4){
			bal = new BalanceSemestral(new Semestral(periodo));
		}else {
			bal = new BalanceAnual(new Anual(periodo));
		}
		
		bal.addCuenta(new Cuenta(nombre, valor));
		empresa.addBalance(bal);
		
		try {
			empresas.addEmpresa(empresa);
		}catch (ElementoYaExiste e) {
			empresas.agregarEmpresaYaExistente(empresa);
		}
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
