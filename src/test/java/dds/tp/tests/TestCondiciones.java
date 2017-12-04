package dds.tp.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.LectorCuentas;
import dds.tp.model.Usuario;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.condicionesTaxativas.CondicionTaxPendiente;
import dds.tp.model.condicionesTaxativas.CondicionTaxativaSimple;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuario;

public class TestCondiciones {
	
	RepositorioEmpresas repoEmpresas;
	RepositorioIndicadores repoIndicadores;
	
	RepositorioUsuario repoUsuarios = new RepositorioUsuario().inicializar();
	Usuario usuarioDefault = repoUsuarios.buscarUsuarioLogueado("default", "default");
	
	@SuppressWarnings("deprecation")
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testsMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testsMetodologia.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario tester = new Usuario("pepe", "");
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados(tester);
	}
	
	@Test
	public void condicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMayor() {		
		CondicionPriorizante condi = new CondicionPriorizante("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Mayor(), 4);
		assert(condi.evaluar(repoEmpresas.getEmpresa("NIKE"), repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	@Test
	public void noSeCumpleCondicionComparadoraConIndicadorRoe4PeriodosAtrasSiendoMenor() {
		CondicionPriorizante condi = new CondicionPriorizante("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Menor(), 4);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoEmpresas.getEmpresa("Puma"), repoIndicadores));
	}
	
	@Test
	public void noSeCumpleCondicionCrecienteDecrecienteRoe4PeriodosAtrasCreciente() {
		CondicionTaxativa condi = new CondicionTaxPendiente("Test", "Para test", repoIndicadores.getIndicador("indicador ROE"), 
				new Mayor(), 4, null);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}
	
	@Test
	public void condicionTaxativaSimpleEnAdidasSiendoRoeMayorQue40SiempreNoSeCumple() {
		CondicionTaxativaSimple condi = new CondicionTaxativaSimple("Test", "Para test", repoIndicadores.getIndicador("Indicador ROE"), new Mayor(), 4, 40.0);
		assert(!condi.evaluar(repoEmpresas.getEmpresa("Adidas"), repoIndicadores));
	}	
}
