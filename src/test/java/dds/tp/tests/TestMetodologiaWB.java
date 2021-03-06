package dds.tp.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.LectorCuentas;
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class TestMetodologiaWB {

	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private RepositorioMetodologias repoMetodologia;
	
	/*MAXIMIZAR ROE: NIKE - ADIDAS - PUMA
	 *MINIMIZAR ENDEDUDAMIENTO: ADIDAS - NIKE - PUMA
	 *LONGEVIDAD: ADIDAS - NIKE - PUMA
	 *TOTAL: ADIDAS(8) NIKE(7) PUMA(3)
	*/
	@SuppressWarnings("deprecation")
	@Before
	public void inicializar() {
		try {
			repoEmpresas = new RepositorioEmpresas(new LectorCuentas("testsMetodologia.txt").obtenerDatos(Files.lines(Paths.get("testsMetodologia.txt"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Usuario tester = new Usuario("pepe", "");
		repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.cargarPredeterminados(tester);
		repoMetodologia = new RepositorioMetodologias();
		repoMetodologia.cargarPredeterminados(repoIndicadores,tester);
	}
	
	@Test
	public void metodologiaWarrenBuffetNoConvienteInvertirEmpresasNuevitaYBadMargen() {
		Metodologia warrenBuffet = repoMetodologia.getMetodologia("Warren Buffet");
		List<ResultadoAnalisis> resultados = warrenBuffet.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		List<ResultadoAnalisis> resultadosNegativos = resultados.stream().filter(elem -> elem.getPuntaje()==0).collect(Collectors.toList());
		assertEquals(resultadosNegativos.size(),2);
	}
	
	@Test
	public void metodologiaWarrenBuffetNoConvienteInvertirEmpresasNuevitaYBadMargenJustificacionesCorrectas() {
		Metodologia warrenBuffet = repoMetodologia.getMetodologia("Warren Buffet");
		List<ResultadoAnalisis> resultados = warrenBuffet.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		List<ResultadoAnalisis> resultadosNegativos = (ArrayList<ResultadoAnalisis>) resultados.stream().filter(elem -> elem.getPuntaje()==0).collect(Collectors.toList());
		ResultadoAnalisis resultadoNuevita = resultadosNegativos.stream().filter(elem -> elem.getEmpresa().getNombre().equalsIgnoreCase("NUEVITA")).findFirst().get();
		ResultadoAnalisis resultadoBadMargen = resultadosNegativos.stream().filter(elem -> elem.getEmpresa().getNombre().equalsIgnoreCase("BADMARGEN")).findFirst().get();
		assertEquals(resultadoNuevita.getJustificacion(), "Esta empresa no cumple una de las condiciones taxativas");
		assertEquals(resultadoBadMargen.getJustificacion(), "Esta empresa no cumple una de las condiciones taxativas");
	}
	
	@Test
	public void metodologiaWarrenBuffetOrdenCorrectoDeLosQueConvieneInvertir() {
		Metodologia warrenBuffet = repoMetodologia.getMetodologia("Warren Buffet");
		List<ResultadoAnalisis> resultados = warrenBuffet.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		assertEquals(resultados.get(0).getEmpresa().getNombre(),"ADIDAS");
		assertEquals(resultados.get(1).getEmpresa().getNombre(),"NIKE");
		assertEquals(resultados.get(2).getEmpresa().getNombre(),"PUMA");
	}
	
	@Test
	public void metodologiaWarrenBuffetPuntajesCorrectos() {
		Metodologia warrenBuffet = repoMetodologia.getMetodologia("Warren Buffet");
		List<ResultadoAnalisis> resultados = warrenBuffet.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		assertEquals(resultados.get(0).getPuntaje(),8);
		assertEquals(resultados.get(1).getPuntaje(),7);
		assertEquals(resultados.get(2).getPuntaje(),3);
		assertEquals(resultados.get(3).getPuntaje(),0);
		assertEquals(resultados.get(4).getPuntaje(),0);
	}
}
