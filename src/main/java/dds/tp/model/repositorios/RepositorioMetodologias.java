package dds.tp.model.repositorios;

import java.util.ArrayList;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Condicion;
import dds.tp.model.Metodologia;
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
		Metodologia warrenBuffet = new Metodologia("WarrenBuffet");	
		Condicion cond1 = new CondicionComparadora("Maximizar ROE", "Maximizar ROE", repoIndicadores.getIndicador("ROE"), new Mayor(), 10);
		Condicion cond2 = new CondicionComparadora("Minimizar DEUDA","Minimizar DEUDA", repoIndicadores.getIndicador("DEUDA"), new Menor(), 1);
		Condicion cond3 = new CondicionVariabilidad("Margenes Crecientes", "Margenes Crecientes", repoIndicadores.getIndicador("MARGEN"), new Mayor(), 10);
		Condicion cond4 = new CondicionLongevidadSimple("Longevidad Simple",  "Longevidad Simple");
		Condicion cond5 = new CondicionLongevidadComparadora("Longevidad Comparadora",  "Longevidad Comparadora");
		warrenBuffet.agregarCondicion(cond1);
		warrenBuffet.agregarCondicion(cond2);
		warrenBuffet.agregarCondicion(cond3);
		warrenBuffet.agregarCondicion(cond4);
		warrenBuffet.agregarCondicion(cond5);
		this.addMetodologia(warrenBuffet);		
	}
	
}
