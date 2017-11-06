package dds.tp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class TestPersistenciaMetodologia {
	RepositorioMetodologias repoMetodologias;
	RepositorioIndicadores repoIndicadores;
	
	@SuppressWarnings("deprecation")
	@Before
	public void inicializar() {
		Usuario tester = new Usuario("pepe", "");
		repoMetodologias = new RepositorioMetodologias();
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados(tester);
		repoMetodologias.cargarPredeterminados(repoIndicadores,tester);
	}
	
	@Test
	public void guardadoDeMetodologiaYCorrectoCargadoJuntoConLasCondiciones() {
		
		repoMetodologias.guardarMetodologia(repoMetodologias.getMetodologias().get(0));
		Metodologia meto = repoMetodologias.cargarMetodologias().get(0);
		repoMetodologias.inicializarCondiciones(meto);
		assertEquals(meto.getCondiciones().size(),5);
	}
	

}
