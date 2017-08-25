package dds.tp.model.criterios;

import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;

public class CriterioEstadistico extends Criterio {
	
	private ModoEstadistico modo;
	
	public CriterioEstadistico(ModoEstadistico modo) {
		this.setModo(modo);
	}

	public ModoEstadistico getModo() {
		return modo;
	}

	public void setModo(ModoEstadistico modo) {
		this.modo = modo;
	}

}
