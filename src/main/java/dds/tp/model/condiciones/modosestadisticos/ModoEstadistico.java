package dds.tp.model.condiciones.modosestadisticos;

import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparable;
import dds.tp.model.repositorios.RepositorioIndicadores;

public abstract class  ModoEstadistico {
	
	public abstract Double getEstadistica(Empresa empresa, Comparable indicador, RepositorioIndicadores repoIndicadores);
	
	public static ModoEstadistico crearModoEstatistico(String nombre) {
		switch (nombre) {
		case "Mediana":
			return new Mediana();
		case "Promedio":
			return new Promedio();
		case "Sumatoria":
			return new Sumatoria();
		default:
			throw new SintaxisIncorrecta("No existe ningun modo con el nombre: " + nombre);
		}
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
