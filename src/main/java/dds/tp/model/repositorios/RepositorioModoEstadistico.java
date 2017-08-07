package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.condiciones.modosestadisticos.Mediana;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.condiciones.modosestadisticos.Promedio;
import dds.tp.model.condiciones.modosestadisticos.Sumatoria;

public class RepositorioModoEstadistico {
	private List<ModoEstadistico> modosEstadisticos;
	
	public RepositorioModoEstadistico() {
		this.modosEstadisticos = new ArrayList<ModoEstadistico>();
		this.modosEstadisticos.add(new Mediana());
		this.modosEstadisticos.add(new Promedio());
		this.modosEstadisticos.add(new Sumatoria());
	}
	
	public List<ModoEstadistico> getModosEstadisticos() {
		return modosEstadisticos;
	}
}
