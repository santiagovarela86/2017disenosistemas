package dds.tp.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.LectorCuentas;
import dds.tp.model.Usuario;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.EvaluadorAntiguedad;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.condicionesTaxativas.CondicionTaxPendiente;
import dds.tp.model.condicionesTaxativas.CondicionTaxativaSimple;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class TestMetodologiasSimples {

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
	}
	@Test
	public void testCondTaxLongSimpleMayor20Pasan4() {
		Metodologia simple1 = new MetodologiaBuilder().setNombre("simple1")
				.agregarCondTaxativa(new CondicionTaxativaSimple("Longevidad Simple", "Longevidad Simple", new EvaluadorAntiguedad(), new Mayor(), 1, 20d))
				.build();
		List<ResultadoAnalisis> resultados = simple1.evaluarEn(repoEmpresas.getEmpresas(), repoIndicadores);
		resultados = resultados.stream().filter(res->!res.getJustificacion().equalsIgnoreCase("Esta empresa no cumple una de las condiciones taxativas")).collect(Collectors.toList());
		assertEquals(resultados.size(),4);
	}
	
}
