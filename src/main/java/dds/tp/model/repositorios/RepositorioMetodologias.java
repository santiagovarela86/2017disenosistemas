package dds.tp.model.repositorios;

import java.util.ArrayList;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Metodologia;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.CondicionComparadora;
import dds.tp.model.condiciones.CondicionVariabilidad;
import dds.tp.model.condiciones.CondicionLongevidadComparadora;
import dds.tp.model.condiciones.CondicionLongevidadSimple;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;

public class RepositorioMetodologias {
	
	private ArrayList<Metodologia> metodologias;
	
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
			.agregarCondPriorizar(new CondicionComparadora("Maximizar ROE", "Maximizar ROE", repoIndicadores.getIndicador("ROE"), new Mayor(), 10))
			.agregarCondPriorizar(new CondicionComparadora("Minimizar DEUDA","Minimizar DEUDA", repoIndicadores.getIndicador("DEUDA"), new Menor(), 1))
			.agregarCondTaxativa(new CondicionVariabilidad("Margenes Crecientes", "Margenes Crecientes", repoIndicadores.getIndicador("MARGEN"), new Mayor(), 10))
			.agregarCondTaxativa(new CondicionLongevidadSimple("Longevidad Simple",  "Longevidad Simple"))
			.agregarCondPriorizar(new CondicionLongevidadComparadora("Longevidad Comparadora",  "Longevidad Comparadora"))
			.build();
		this.addMetodologia(warrenBuffet);
	}
	
}
