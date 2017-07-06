package dds.tp.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.LectorCuentas;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class TestMetodologia {

	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private RepositorioMetodologias repoMetodologia;
	private ArrayList<CondicionPriorizar> condiciones;
	
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testMetodologia.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados();
		repoMetodologia = new RepositorioMetodologias();
		repoMetodologia.cargarPredeterminados(repoIndicadores);
		condiciones = new ArrayList<CondicionPriorizar>();
	}
	
	@Test
	public void metodologiaWarrenBuffetEliminaEmpresasNuevitaYBadMargen() {
		//repoMetodologia.getMetodologias().get(0)
	}
}
