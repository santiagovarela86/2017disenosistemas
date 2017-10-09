package dds.tp.model.metodologia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.excepciones.NoHayCondiciones;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
public class Metodologia {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="metodologia_id")
	@Where(clause="tipobalance='condTaxativa'")
	private List<CondicionTaxativa> condicionesTaxativas;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="metodologia_id")
	@Where(clause="tipobalance='condPriorizante'")
	private List<CondicionPriorizante> condicionesQuePriorizan;

	
	public Metodologia() {
		
	}
	
	public Metodologia(String nombre, ArrayList<CondicionTaxativa> condicionesTaxativas, ArrayList<CondicionPriorizante> condicionesQuePriorizan) {
		if(nombre == null || nombre.equalsIgnoreCase(""))
			throw new ElementNotLoad("No se aceptan nombres vacios");
		if(condicionesQuePriorizan.isEmpty() && condicionesTaxativas.isEmpty())
			throw new NoHayCondiciones();
		this.nombre = nombre;
		this.condicionesQuePriorizan = condicionesQuePriorizan;
		this.condicionesTaxativas = condicionesTaxativas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<ResultadoAnalisis> evaluarEn(List<Empresa> empresasAAnalizar, RepositorioIndicadores repoIndicadores){
		List<Empresa> empresasParaAnalizar = new ArrayList<>(empresasAAnalizar);
		
		List<ResultadoAnalisis> resultadosConDatosInsuficientes = empresasParaAnalizar.stream()
			.filter(empresa -> this.getCondiciones().stream().anyMatch(cond -> !cond.empresaPuedeSerEvaluada(empresa, repoIndicadores)))
			.map(empresa -> ResultadoAnalisis.crearResultadoConDatosInsuficientes(empresa))
			.collect(Collectors.toList());
		
		List<Empresa> empresasConLoNecesario = empresasParaAnalizar.stream()
			.filter(empresa -> this.getCondiciones().stream().allMatch(cond -> cond.empresaPuedeSerEvaluada(empresa, repoIndicadores)))
			.collect(Collectors.toList());
			
		List<ResultadoAnalisis> resultadosQueNoCumplenCondicionesTaxativas = empresasConLoNecesario.stream().filter(empresa->condicionesTaxativas.stream()
			.anyMatch(condicion -> !condicion.evaluar(empresa, repoIndicadores)))
			.map(empresa -> ResultadoAnalisis.crearResultadoNoCumpleCondiconTaxativa(empresa))
			.collect(Collectors.toList());
		
		List<Empresa> empresasPasanTaxativas = empresasConLoNecesario.stream().filter(empresa->condicionesTaxativas.stream()
				.allMatch((condicion -> condicion.evaluar(empresa, repoIndicadores))))
				.collect(Collectors.toList());
		
		List<ResultadoAnalisis> resultadosEmpresasOrdenados = new Ordenador().getResultados(empresasPasanTaxativas, condicionesQuePriorizan, repoIndicadores);
		List<ResultadoAnalisis> resultadosFinales = new ArrayList<>();
		resultadosFinales.addAll(resultadosEmpresasOrdenados);
		resultadosFinales.addAll(resultadosQueNoCumplenCondicionesTaxativas);
		resultadosFinales.addAll(resultadosConDatosInsuficientes);
		return resultadosFinales;
	}
	
	public void setCondicionesQuePriorizan(List<CondicionPriorizante> condicionesQuePriorizan) {
		this.condicionesQuePriorizan = condicionesQuePriorizan;
	}
	
	public void setCondicionesTaxativas(List<CondicionTaxativa> condicionesTaxativas) {
		this.condicionesTaxativas = condicionesTaxativas;
	}
	
	public List<Condicion> getCondiciones() {
		ArrayList<Condicion> allCondiciones = new ArrayList<>(this.condicionesQuePriorizan);
		allCondiciones.addAll(condicionesTaxativas);
		return allCondiciones;
	}
	
	public void removerEmpresasYaAnalizadas(List<Empresa> empresas, List<ResultadoAnalisis> resultados) {
		resultados.stream()
				.map(analisis -> analisis.getEmpresa())
				.forEach(empr -> empresas.remove(empr));
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
