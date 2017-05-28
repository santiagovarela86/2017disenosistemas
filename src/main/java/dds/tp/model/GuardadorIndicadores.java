package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class GuardadorIndicadores {

	private List<Indicador> indicadores;
	
	public GuardadorIndicadores() {
		super();
		this.indicadores = new ArrayList<>();
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void addIndicador(Indicador indc) throws RuntimeErrorException {
		if(this.indicadores.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(indc.getNombre())))
		{
			throw new RuntimeErrorException(new Error("Ya existe un indicador con este nombre"));
		}
		this.indicadores.add(indc);
	}
}
