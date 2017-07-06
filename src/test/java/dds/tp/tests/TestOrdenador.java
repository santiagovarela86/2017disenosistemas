package dds.tp.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionLongevidadComparadora;
import dds.tp.model.metodologia.Ordenador;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class TestOrdenador {

	private RepositorioEmpresas repoEmpresas;
	private ArrayList<CondicionPriorizar> condiciones;
	
	@Before
	public void inicializar() {
		repoEmpresas = new RepositorioEmpresas();
		condiciones = new ArrayList<CondicionPriorizar>();
		repoEmpresas.addEmpresa(new Empresa("Test1", 1960));
		repoEmpresas.addEmpresa(new Empresa("Test2", 1950));
		repoEmpresas.addEmpresa(new Empresa("Test3", 1970));
	}
	
	@Test
	public void ordenamientoPorLongevidadTest1Test2Tes3() {
		ArrayList<Empresa> resultado = new Ordenador()
				.generarListaOrdenada(repoEmpresas.getEmpresas(), 
						new CondicionLongevidadComparadora("CondTest", "Condicion longevidad comparadora"), 
						new RepositorioIndicadores());
		assertEquals(resultado.get(0).getNombre(),"Test2");
		assertEquals(resultado.get(1).getNombre(),"Test1");
		assertEquals(resultado.get(2).getNombre(),"Test3");
	}
}
