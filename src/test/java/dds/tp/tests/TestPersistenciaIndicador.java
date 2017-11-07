package dds.tp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class TestPersistenciaIndicador {

	RepositorioIndicadores repoIndicadores;
	
	RepositorioUsuarios repoUsuarios = new RepositorioUsuarios().inicializar();
	Usuario usuarioDefault = repoUsuarios.getUsuario("default");
	
	@Before
	public void inicializar() {
		repoIndicadores = new RepositorioIndicadores();
	}
	
	@Test
	public void guardadoDeIndicadores() {
		repoIndicadores.guardarIndicador(new Indicador("Roe", "5+5", usuarioDefault));
		repoIndicadores.guardarIndicador(new Indicador("Pepe", "Roe+5", usuarioDefault));
		assertEquals(repoIndicadores.cargarIndicadores().size(), 2);
	}
}
