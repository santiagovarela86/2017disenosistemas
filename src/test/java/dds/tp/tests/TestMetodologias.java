package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dds.tp.model.Empresa;
import dds.tp.model.Metodologia;
import dds.tp.model.ResultadoMetodologia;
import dds.tp.model.condiciones.Longevidad;

public class TestMetodologias {

	@Test
	public void noSeCumpleLongevidadMayorA10() {
		Metodologia meto = new Metodologia("Test");
		meto.agregarCondicion(new Longevidad().setEdad(10));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe"));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void seCumpleLongevidadMayorA4() {
		Metodologia meto = new Metodologia("Test");
		meto.agregarCondicion(new Longevidad().setEdad(4));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe"));
		assertEquals("Si", resultadoMetodologia.getConvieneInvertir());
	}
	
}
