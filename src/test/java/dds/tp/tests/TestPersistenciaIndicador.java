package dds.tp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class TestPersistenciaIndicador {

	RepositorioIndicadores repoIndicadores;
	
	@Before
	public void inicializar() {
		repoIndicadores = new RepositorioIndicadores();
	}
	
	@Test
	public void guardadoDeIndicadores() {
		repoIndicadores.guardarIndicador(new Indicador("Roe", "5+5"));
		repoIndicadores.guardarIndicador(new Indicador("Pepe", "Roe+5"));
		assertEquals(repoIndicadores.cargarIndicadores().size(), 2);
	}
}
