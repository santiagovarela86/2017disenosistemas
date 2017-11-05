package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.LectorCuentas;
import dds.tp.model.Usuario;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioUsuarios;

public class TestOrdenador {

	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private ArrayList<CondicionPriorizante> condiciones;
	
	RepositorioUsuarios repoUsuarios = new RepositorioUsuarios().obtenerRepoCompleto();
	Usuario usuarioDefault = repoUsuarios.getUsuario("default");
	
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testOrdenador.txt").obtenerDatos(Files.lines(Paths.get("testOrdenador.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repoIndicadores = new RepositorioIndicadores();
		condiciones = new ArrayList<CondicionPriorizante>();
		
	}
	
	//EN ESTE MODELO FALTA IMPLEMENTAR LA FUNCIONALIDAD DE LA LONGEVIDAD
	/*
	@Test
	public void ordenamientoPorLongevidadTest1Test2Tes3() {
		condiciones.clear();
		List<Empresa> resultado = new Ordenador()
				.generarListaOrdenada(repoEmpresas.getEmpresas(), 
						new CondicionLongevidadComparadora("CondTest", "Condicion longevidad comparadora"), 
						new RepositorioIndicadores());
		assertEquals(resultado.get(0).getNombre(),"Test2");
		assertEquals(resultado.get(1).getNombre(),"Test1");
		assertEquals(resultado.get(2).getNombre(),"Test3");
	}
	*/
	
	@Test
	public void ordenamientoPorRoeResultadoTest3Test2Test1() {
		condiciones.clear();
		List<Empresa> resultado = new Ordenador()
				.generarListaOrdenada(repoEmpresas.getEmpresas(), 
						new CondicionPriorizante("CondTest", "Condicion longevidad comparadora", 
						new Indicador("ROE", "roe", usuarioDefault),new Mayor(),1), repoIndicadores);
		
		assertEquals(resultado.get(0).getNombre(),"Test3");
		assertEquals(resultado.get(1).getNombre(),"Test2");
		assertEquals(resultado.get(2).getNombre(),"Test1");
	}
	
	@Test
	public void puntajePorRoeResultadoTest3Test2Test1() {
		condiciones.clear();
		condiciones.add((new CondicionPriorizante("CondTest", "Condicion longevidad comparadora", 
			new Indicador("ROE", "roe", usuarioDefault),new Mayor(),1)));
		
		List<ResultadoAnalisis> resultado = new Ordenador()
				.getResultados(repoEmpresas.getEmpresas(), condiciones,repoIndicadores);
		
		assertEquals(resultado.get(0).getPuntaje(),3);
		assertEquals(resultado.get(1).getPuntaje(),2);
		assertEquals(resultado.get(2).getPuntaje(),1);
	}
	
	//EN ESTE MODELO FALTA IMPLEMENTAR LA FUNCIONALIDAD DE LA LONGEVIDAD
	/*
	
	@Test
	public void puntajePorLongevidadYRoePorPuntajeTest1Test2Tes3() {
		condiciones.clear();
		condiciones.add((new CondicionLongevidadComparadora("Longevidad", "Test longevidad")));
		
		condiciones.add((new CondicionComparadora("CondTest", 
				"Condicion longevidad comparadora", 
				new Indicador("ROE", "roe"),new Mayor(),1)));
		
		List<ResultadoAnalisis> resultado = new Ordenador()
				.getResultados(repoEmpresas.getEmpresas(), condiciones,repoIndicadores);
						
		assertEquals(resultado.get(0).getEmpresa().getNombre(),"Test2");
		assertEquals(resultado.get(1).getEmpresa().getNombre(),"Test3");
		assertEquals(resultado.get(2).getEmpresa().getNombre(),"Test1");
	}
	
	*/
	
}
