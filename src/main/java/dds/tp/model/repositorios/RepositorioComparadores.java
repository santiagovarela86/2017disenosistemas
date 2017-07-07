package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.MayorIgual;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.condiciones.comparadores.MenorIgual;

public class RepositorioComparadores {

	private List<Comparador> comparadores;
	
	public RepositorioComparadores() {
		this.comparadores = new ArrayList<Comparador>();
		this.comparadores.add(new Mayor());
		this.comparadores.add(new MayorIgual());
		this.comparadores.add(new Menor());
		this.comparadores.add(new MenorIgual());
	}
	
	public List<Comparador> getComparadores() {
		return comparadores;
	}
	
}
