package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

public class CondicionLongevidadSimple extends Condicion {

	public CondicionLongevidadSimple(String nombre, String descripcion) {
		super(nombre, descripcion);
	}

	public boolean evaluar(Empresa empresa){
		return empresa.getAntiguedad() > 10;
	}
	
}
