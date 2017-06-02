package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dds.tp.evaluador.EvaluadorIndicador;
import dds.tp.excepciones.CuentaNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.IndicadorNotFound;
import dds.tp.lexer.ParseException;
import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;
import dds.tp.model.Parser;

public class TestCalcularExpresiones {

	private Balance balanceTest;
	private GuardadorIndicadores baulIndicadores;
	
	@Before
	public void inicializar() {
		baulIndicadores = new GuardadorIndicadores();
		balanceTest = new Balance("2017");
		try {
			balanceTest.addCuenta(new Cuenta("Ebitda", 200f));
			balanceTest.addCuenta(new Cuenta("Roe", 20f));
			balanceTest.addCuenta(new Cuenta("FDS", 102.0f));
		} catch (ElementoYaExiste e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCalculoIndicadoresNoErrors() throws ElementoYaExiste, ParseException, CuentaNotFound, IndicadorNotFound {
		//Testeo de cuentas mas operaciones con numeros y con cuentas
		EvaluadorIndicador eva1 = new EvaluadorIndicador(new Indicador("test", "cuenta(fds)*2+5"), balanceTest, baulIndicadores);
		EvaluadorIndicador eva2 = new EvaluadorIndicador(new Indicador("test", "cuenta(eBiTda)-cuenta(roe)+3"), balanceTest, baulIndicadores);
		assertEquals(209, eva1.evaluar(), 0);
		assertEquals(183, eva2.evaluar(), 0);
		//----------------------
		//Testeo con indicadores cuentas y numeros
		baulIndicadores.addIndicador(new Indicador("test", "cuenta(fds)*2+5"));
		EvaluadorIndicador eva3 = new EvaluadorIndicador(new Indicador("test", "indicador(test)+6"), balanceTest, baulIndicadores);
		assertEquals(215, eva3.evaluar(), 0);
		//----------------------
	}

	@Test(expected=ParseException.class)
	public void testParserError() throws ParseException{
		new Parser().chequearExpresion("pepe+7");
	}
	
	@Test(expected=ElementoYaExiste.class)
	public void TestNoSePermitenIndicadoresConElMismoNombre() throws ElementoYaExiste {
		baulIndicadores.addIndicador(new Indicador("test", "cuenta(fsd)*2+5"));
		baulIndicadores.addIndicador(new Indicador("test", "cuenta(fsd)*7+5"));
		
	}
	
	@Test(expected=CuentaNotFound.class)
	public void TestErrorAlCalcularYNoTenerLaCuentaEnElBalance() throws CuentaNotFound, ParseException, IndicadorNotFound {
		EvaluadorIndicador eva1 = new EvaluadorIndicador(new Indicador("test", "cuenta(pepe)*2+5"), balanceTest, baulIndicadores);
		eva1.evaluar();
	
	}
	
	@Test(expected=IndicadorNotFound.class)
	public void TestErrorAlCalcularYNoTenerLosIndicadores() throws CuentaNotFound, ParseException, IndicadorNotFound {
		EvaluadorIndicador eva1 = new EvaluadorIndicador(new Indicador("test", "indicador(pepe)*2+5"), balanceTest, baulIndicadores);
		eva1.evaluar();
	
	}
	
}
