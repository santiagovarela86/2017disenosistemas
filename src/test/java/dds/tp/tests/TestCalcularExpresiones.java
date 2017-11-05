package dds.tp.tests;



import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dds.tp.calculador.Expresion;
import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Balance;
import dds.tp.model.BalanceAnual;
import dds.tp.model.Cuenta;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuarios;
import dds.tp.parsertools.Parser;

public class TestCalcularExpresiones {

	private Balance balanceTest;
	private RepositorioIndicadores baulIndicadores;
	
	RepositorioUsuarios repoUsuarios = new RepositorioUsuarios().obtenerRepoCompleto();
	Usuario usuarioDefault = repoUsuarios.getUsuario("default");
	
	@Before
	public void inicializar() {
		baulIndicadores = new RepositorioIndicadores();
		balanceTest = new BalanceAnual(new Anual("2017"));
		try {
			balanceTest.addCuenta(new Cuenta("Ebitda", 200d));
			balanceTest.addCuenta(new Cuenta("Roe", 20d));
			balanceTest.addCuenta(new Cuenta("FDS", 102.0d));
		} catch (ElementoYaExiste e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCalculoIndicadoresNoErrorsConSoloNumeros()  {
		Indicador uno = new Indicador("test", new Expresion(new Parser().parsear("7+5*2")), usuarioDefault);
		Indicador dos = new Indicador("testuno", new Expresion(new Parser().parsear("7+9/3")), usuarioDefault);
		
		assertEquals(new Double(17), uno.evaluar(balanceTest, baulIndicadores));
		assertEquals(new Double(10), dos.evaluar(balanceTest, baulIndicadores));
	}
	
	@Test
	public void testCalculoIndicadoresNoErrorsConCuentaYNumeros()  {
		Indicador uno = new Indicador("test", new Expresion(new Parser().parsear("7+Roe*2")), usuarioDefault);
		Indicador dos = new Indicador("test", new Expresion(new Parser().parsear("7+FDS*2+5+9/3")), usuarioDefault);
		
		assertEquals(new Double(47), uno.evaluar(balanceTest, baulIndicadores));
		assertEquals(new Double(219), dos.evaluar(balanceTest, baulIndicadores));
	}
	
	@Test
	public void testCalculoIndicadoresNoErrorsConCuentaYNumerosEIndicadores()  {
		Indicador uno = new Indicador("test", new Expresion(new Parser().parsear("10+Roe*2")), usuarioDefault);
		Indicador dos = new Indicador("testuno", new Expresion(new Parser().parsear("test/5*2+5")), usuarioDefault);
		baulIndicadores.addIndicador(uno);
		assertEquals(50, uno.evaluar(balanceTest, baulIndicadores), 0);
		assertEquals(10, dos.evaluar(balanceTest, baulIndicadores), 0);
	}

	@Test(expected=SintaxisIncorrecta.class)
	public void testParserError(){
		new Parser().parsear("pepe++@7");
	}
	
	@Test(expected=ElementoYaExiste.class)
	public void TestNoSePermitenIndicadoresConElMismoNombre(){
		Indicador uno = new Indicador("test", new Expresion(new Parser().parsear("10+Roe*2")), usuarioDefault);
		Indicador dos = new Indicador("test", new Expresion(new Parser().parsear("test/5*2+5")), usuarioDefault);
		baulIndicadores.addIndicador(uno);
		baulIndicadores.addIndicador(dos);
	}
	
	@Test(expected=ElementoNotFound.class)
	public void TestErrorAlCalcularYNoTenerLaCuentaOElIdentificador()  {
		Indicador uno = new Indicador("test", new Expresion(new Parser().parsear("10+pepe*2")), usuarioDefault);
		uno.evaluar(balanceTest, baulIndicadores);
	}
	

	
}
