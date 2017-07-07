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
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testsMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testsMetodologia.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados();
	}
	
	@Test
	public void condicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMayor() {
		CondicionComparadora condi = new CondicionComparadora("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Mayor(), 4);
		assert(condi.evaluar(repoEmpresas.getEmpresa("NIKE"), repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	@Test
	public void noSeCumpleCondicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMenor() {
		CondicionComparadora condi = new CondicionComparadora("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Menor(), 4);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoEmpresas.getEmpresa("Puma"), repoIndicadores));
	}
	
	@Test
	public void noSeCumpleCondicionCrecienteDecrecienteRoe4PeriodosAtrasCreciente() {
		CondicionVariabilidad condi = new CondicionVariabilidad("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Mayor(), 4);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	@Test
	public void condicionCrecienteDecrecienteRoe4PeriodosAtrasCrecienteNoSeCumple() {
		CondicionVariabilidad condi = new CondicionVariabilidad("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Mayor(), 4);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	
}
