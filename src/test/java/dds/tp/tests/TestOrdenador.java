package dds.tp.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.tp.calculador.Expresion;
import dds.tp.model.Balance;
import dds.tp.model.BalanceAnual;
import dds.tp.model.CondicionPriorizar;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.CondicionComparadora;
import dds.tp.model.condiciones.CondicionLongevidadComparadora;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.Parser;

public class TestOrdenador {

	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private ArrayList<CondicionPriorizar> condiciones;
	
	@Before
	public void inicializar() {
		repoEmpresas = new RepositorioEmpresas();
		repoIndicadores = new RepositorioIndicadores();
		condiciones = new ArrayList<CondicionPriorizar>();
		repoEmpresas.addEmpresa(new Empresa("Test1", 1960));
		repoEmpresas.addEmpresa(new Empresa("Test2", 1950));
		repoEmpresas.addEmpresa(new Empresa("Test3", 1970));
		repoEmpresas.getEmpresa("Test1").addBalance(new BalanceAnual(new Anual(2017)));
		repoEmpresas.getEmpresa("Test2").addBalance(new BalanceAnual(new Anual(2017)));
		repoEmpresas.getEmpresa("Test3").addBalance(new BalanceAnual(new Anual(2017)));
		repoEmpresas.getEmpresa("Test1").getBalance("2017").addCuenta(new Cuenta("Roe", 100d));
		repoEmpresas.getEmpresa("Test2").getBalance("2017").addCuenta(new Cuenta("Roe", 196d));
		repoEmpresas.getEmpresa("Test3").getBalance("2017").addCuenta(new Cuenta("Roe", 906d));
		condiciones.add((new CondicionComparadora("CondTest", 
				"Condicion longevidad comparadora", 
				new Indicador("ROE", new Expresion(new Parser().parsear("roe"))),new Mayor(),1)));
	}
	
	@Test
	public void ordenamientoPorLongevidadTest1Test2Tes3() {
		ArrayList<Empresa> resultado = new Ordenador()
				.generarListaOrdenada(repoEmpresas.getEmpresas(), 
						new CondicionLongevidadComparadora("CondTest", "Condicion longevidad comparadora"), 
						new RepositorioIndicadores());
		assertEquals(resultado.get(0).getNombre(),"Test2");
		assertEquals(resultado.get(1).getNombre(),"Test1");
		assertEquals(resultado.get(2).getNombre(),"Test3");
	}
	
	@Test
	public void ordenamientoPorRoeResultadoTest3Test2Test1() {
		ArrayList<Empresa> resultado = new Ordenador()
				.generarListaOrdenada(repoEmpresas.getEmpresas(), 
						new CondicionComparadora("CondTest", 
								"Condicion longevidad comparadora", 
								new Indicador("ROE", new Expresion(new Parser().parsear("roe"))),new Mayor(),1), 
						repoIndicadores);
		assertEquals(resultado.get(0).getNombre(),"Test3");
		assertEquals(resultado.get(1).getNombre(),"Test2");
		assertEquals(resultado.get(2).getNombre(),"Test1");
	}
	
	@Test
	public void puntajePorRoeResultadoTest3Test2Test1() {
		List<ResultadoAnalisis> resultado = new Ordenador()
				.getResultados(repoEmpresas.getEmpresas(), condiciones,repoIndicadores);

		assertEquals(resultado.get(0).getPuntaje(),3);
		assertEquals(resultado.get(1).getPuntaje(),2);
		assertEquals(resultado.get(2).getPuntaje(),1);
	}
}
