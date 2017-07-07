package dds.tp.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.LectorCuentas;
import dds.tp.model.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
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
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testsMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testsMetodologia.txt"))));
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
	public void metodologiaWarrenBuffetNoConvienteInvertirEmpresasNuevitaYBadMargen() {
		Metodologia warrenBuffet = repoMetodologia.getMetodlogia("Warren Buffet");
		ArrayList<ResultadoAnalisis> resultados = warrenBuffet.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		ArrayList<ResultadoAnalisis> resultadosNegativos = (ArrayList<ResultadoAnalisis>) resultados.stream().filter(elem -> elem.getPuntaje()==0).collect(Collectors.toList());
		assertEquals(resultados.size(),5);
		assertEquals(resultadosNegativos.size(),2);
	}
}
