package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

import dds.tp.excepciones.ElementoYaExiste;

public class GuardadorIndicadores {

	private List<Indicador> indicadores;
	
	public GuardadorIndicadores() {
		super();
		this.indicadores = new ArrayList<>();
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void addIndicador(Indicador indc) throws ElementoYaExiste {
		if(this.indicadores.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(indc.getNombre())))
		{
			throw new ElementoYaExiste("Ya existe un indicador con este nombre");
		}
		this.indicadores.add(indc);
	}
}
