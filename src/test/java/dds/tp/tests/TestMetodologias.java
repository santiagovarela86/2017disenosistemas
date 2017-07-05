package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Empresa;
import dds.tp.model.Metodologia;
import dds.tp.model.ResultadoMetodologia;
import dds.tp.model.builders.CondicionMultipleBuilder;
import dds.tp.model.condiciones.CondicionMultiple;
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
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe",2010));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void seCumpleLongevidadMayorA4() {
		meto.agregarCondicion(new Longevidad().setEdad(4));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe",2010));
		assertEquals("Si", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void noSeCumpleQueUnaEmpresa5AniosEsMasAntiguaQueOtra5Anios() {
		meto.agregarCondicion(new MasAntiguaQue().setEmpresaAComparar(new Empresa("AComparar",1999)));
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe",1999));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void seCumpleCondicionMultipleLongevidadMayorA3YLongevidadMayorA4(){
		CondicionMultiple condMult = 
				new CondicionMultipleBuilder().setNombre("Test")
				.setDescripcion("Solo para test")
				.agregarCondicion(new Longevidad().setEdad(4))
				.agregarCondicion(new Longevidad().setEdad(3))
				.build();
		meto.agregarCondicion(condMult);
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe",2010));
		assertEquals("Si", resultadoMetodologia.getConvieneInvertir());
	}
	
	@Test
	public void noSeCumpleCondicionMultipleLongevidadMayorA10YLongevidadMayorA4(){
		CondicionMultiple condMult = 
				new CondicionMultipleBuilder().setNombre("Test")
				.setDescripcion("Solo para test")
				.agregarCondicion(new Longevidad().setEdad(10))
				.agregarCondicion(new Longevidad().setEdad(3))
				.build();
		meto.agregarCondicion(condMult);
		ResultadoMetodologia resultadoMetodologia = meto.evaluarEn(new Empresa("Pepe",2010));
		assertEquals("No", resultadoMetodologia.getConvieneInvertir());
	}
	
}
