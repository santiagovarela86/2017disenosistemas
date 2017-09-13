package dds.tp.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.model.Empresa;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class TestPersistenciaMetodologia {
	RepositorioMetodologias repoMetodologias;
	RepositorioIndicadores repoIndicadores;
	
	@Before
	public void inicializar() {
		repoMetodologias = new RepositorioMetodologias();
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados();
		repoMetodologias.cargarPredeterminados(repoIndicadores);
	}
	
	@Test
	public void guardadoDeMetodologiaYCorrectoCargadoJuntoConLasCondiciones() {
		
		repoMetodologias.guardarMetodologia(repoMetodologias.getMetodologias().get(0));
		Metodologia meto = repoMetodologias.cargarMetodologias().get(0);
		repoMetodologias.inicializarCondiciones(meto);
		assertEquals(meto.getCondiciones().size(),5);
	}
	

}
