package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

//ESTA SE USA PARA ORDENAR
public class CondicionLongevidadComparadora extends Condicion {
	
	public CondicionLongevidadComparadora(String nombre, String descripcion) {
		super(nombre,descripcion);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2){
		return empresa1.getAntiguedad() > empresa2.getAntiguedad();		
	}

}
