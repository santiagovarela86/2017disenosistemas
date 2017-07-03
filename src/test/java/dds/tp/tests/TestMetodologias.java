package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Empresa;
import dds.tp.model.Metodologia;
import dds.tp.model.ResultadoMetodologia;
import dds.tp.model.condiciones.Longevidad;
import dds.tp.model.condiciones.MasAntiguaQue;

public class TestMetodologias {
	private Metodologia meto;
	@Before
	public void inicializar() {
		meto = new Metodologia("Test");
	}
	
	@Test
	public void noSeCumpleLongevidadMayorA10() {
		meto.agregarCondicion(new Longevidad().setEdad(10));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe"));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void seCumpleLongevidadMayorA4() {
		meto.agregarCondicion(new Longevidad().setEdad(4));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe"));
		assertEquals("Si", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void noSeCumpleQueUnaEmpresa5AniosEsMasAntiguaQueOtra5Anios() {
		meto.agregarCondicion(new MasAntiguaQue().setEmpresaAComparar(new Empresa("AComparar")));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe"));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
}
