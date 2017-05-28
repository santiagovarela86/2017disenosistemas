package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

public class GuardadorIndicadores {

	private List<Indicador> indicadores;
	
	public GuardadorIndicadores() {
		super();
		this.indicadores = new ArrayList<>();
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void addIndicador(Indicador indc) {
		this.indicadores.add(indc);
	}
}
