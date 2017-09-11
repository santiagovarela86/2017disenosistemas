package dds.tp.model.repositorios;

import java.util.ArrayList;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.EvaluadorAntiguedad;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.criterios.CriterioComparador;
import dds.tp.model.criterios.CriterioPendiente;
import dds.tp.model.metodologia.Metodologia;

public class RepositorioMetodologias {
	
	private ArrayList<Metodologia> metodologias = new ArrayList<>();
	
	public ArrayList<Metodologia> getMetodologias() {
		return metodologias;
	}
	
	public void setMetodologias(ArrayList<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public boolean contieneMetodologia(String nombre) {
		return this.metodologias.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}

	public void addMetodologia(Metodologia metodologia) throws ElementoYaExiste {
		if(this.contieneMetodologia(metodologia.getNombre())) {
			throw new ElementoYaExiste("Ya existe una metodologia con este nombre");
		}
		this.metodologias.add(metodologia);
	}

	public void cargarPredeterminados(RepositorioIndicadores repoIndicadores) {
		Metodologia warrenBuffet = new MetodologiaBuilder().setNombre("Warren Buffet")
			.agregarCondPriorizar(new CondicionPriorizante("Maximizar ROE", "Maximizar ROE", repoIndicadores.getIndicador("Indicador ROE"), new Mayor(), 7))
			.agregarCondPriorizar(new CondicionPriorizante("Minimizar DEUDA","Minimizar DEUDA", repoIndicadores.getIndicador("Indicador ENDEUDAMIENTO"), new Menor(), 1))
			.agregarCondTaxativa(new CondicionTaxativa("Margenes Crecientes", "Margenes Crecientes", repoIndicadores.getIndicador("Indicador MARGEN"), new Menor(), 8, new CriterioPendiente(), null))
			.agregarCondTaxativa(new CondicionTaxativa("Longevidad Simple", "Longevidad Simple", new EvaluadorAntiguedad(), new Mayor(), 1, new CriterioComparador(), 10d))
			.agregarCondPriorizar(new CondicionPriorizante("Mas Antigua", "Mas Antigua", new EvaluadorAntiguedad(), new Mayor(), 1))			
			.build();
		this.addMetodologia(warrenBuffet);
	}
	
	public Metodologia getMetodlogia(String nombre) {
		if(!this.contieneMetodologia(nombre)){
			throw new ElementoNotFound("No se encontro la metodologia " + nombre);
		}
		return this.metodologias.stream()
				.filter(metodologia -> metodologia.getNombre().equalsIgnoreCase(nombre))
				.findFirst().get();
	}
	
}
