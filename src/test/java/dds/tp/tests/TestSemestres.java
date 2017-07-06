package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dds.tp.model.periodos.Semestral;

public class TestSemestres {
	
	@Test
	public void creacionPrimerSemestre2017() {
		Semestral semestre = new Semestral("1ER SEMESTRE 2017");
		assertEquals(semestre.getAnio(), 2017);
		assertEquals(semestre.getNumeroDeSemestre(), 1);
	}
	
	@Test
	public void creacionSegundoSemestre2014() {
		Semestral semestre = new Semestral("2DO SEMESTRE 2014");
		assertEquals(semestre.getAnio(), 2014);
		assertEquals(semestre.getNumeroDeSemestre(), 2);
	}
	
	@Test
	public void siguienteSemesteDeSegundoSemestre2014EsPrimerSemestre2015() {
		Semestral semestre = new Semestral("2DO SEMESTRE 2014").semestreSiguiente();
		assertEquals(semestre.getAnio(), 2015);
		assertEquals(semestre.getNumeroDeSemestre(), 1);
	}

	@Test
	public void siguienteSemesteDePrimerSemestre2014EsSegundoSemestre2014() {
		Semestral semestre = new Semestral("1ER SEMESTRE 2014").semestreSiguiente();
		assertEquals(semestre.getAnio(), 2014);
		assertEquals(semestre.getNumeroDeSemestre(), 2);
	}
	
	@Test
	public void anteriorSemesteDePrimerSemestre2014EsSegundoSemestre2013() {
		Semestral semestre = new Semestral("1ER SEMESTRE 2014").semestreAnterior();
		assertEquals(semestre.getAnio(), 2013);
		assertEquals(semestre.getNumeroDeSemestre(), 2);
	}
	
	@Test
	public void siguienteSemesteDeSegundoSemestre2014EsPrimerSemestre2014() {
		Semestral semestre = new Semestral("2DO SEMESTRE 2014").semestreAnterior();
		assertEquals(semestre.getAnio(), 2014);
		assertEquals(semestre.getNumeroDeSemestre(), 1);
	}
	
	@Test
	public void PrimerSemestre2014EsIgualAPrimerSemestre2014() {
		Semestral semestre = new Semestral("1ER SEMESTRE 2014");
		Semestral semestre2 = new Semestral(1,2014);
		assert(semestre.igualA(semestre2));
	}
	
	@Test
	public void PrimerSemestre2014NoEsIgualAPrimerSemestre2015() {
		Semestral semestre = new Semestral("1ER SEMESTRE 2014");
		Semestral semestre2 = new Semestral(1,2015);
		assert(!semestre.igualA(semestre2));
	}
}
