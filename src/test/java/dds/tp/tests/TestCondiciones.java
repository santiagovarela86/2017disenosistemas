package dds.tp.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.LectorCuentas;
import dds.tp.model.condiciones.CondicionComparadora;
import dds.tp.model.condiciones.CondicionVariabilidad;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class TestCondiciones {
	
	RepositorioEmpresas repoEmpresas;
	RepositorioIndicadores repoIndicadores;
	
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("C:\\Users\\Ezequiel\\Desktop\\Tp DDS\\2017-vn-group-15\\tests.txt").obtenerDatos(Files.lines(Paths.get("C:\\Users\\Ezequiel\\Desktop\\Tp DDS\\2017-vn-group-15\\tests.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados();
	}
	
	@Test
	public void condicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMayor() {
		CondicionComparadora condi = new CondicionComparadora("Test", "Para test", repoIndicadores.getIndicador("ROE"), 
				new Mayor(), 4);
		assert(condi.evaluar(repoEmpresas.getEmpresa("NIKE"), repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	@Test
	public void condicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMenor() {
		CondicionComparadora condi = new CondicionComparadora("Test", "Para test", repoIndicadores.getIndicador("ROE"), 
				new Menor(), 4);
		assert(condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoEmpresas.getEmpresa("Puma"), repoIndicadores));
	}
	
	@Test
	public void condicionCrecienteDecrecienteRoe4PeriodosAtrasCreciente() {
		CondicionVariabilidad condi = new CondicionVariabilidad("Test", "Para test", repoIndicadores.getIndicador("ROE"), 
				new Mayor(), 4);
		assert(condi.evaluar(repoEmpresas.getEmpresa("NIKE"), repoIndicadores));
	}
	
	@Test
	public void condicionCrecienteDecrecienteRoe4PeriodosAtrasCrecienteNoSeCumple() {
		CondicionVariabilidad condi = new CondicionVariabilidad("Test", "Para test", repoIndicadores.getIndicador("ROE"), 
				new Mayor(), 4);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	
}