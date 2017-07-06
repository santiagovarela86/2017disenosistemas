package dds.tp.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.LectorCuentas;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class TestMetodologia {

	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private ArrayList<CondicionPriorizar> condiciones;
	
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testOrdenador.txt").obtenerDatos(Files.lines(Paths.get("testOrdenador.txt"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repoIndicadores = new RepositorioIndicadores();
		condiciones = new ArrayList<CondicionPriorizar>();
		
	}
}
