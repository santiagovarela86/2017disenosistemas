package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;

//ESTA SE USA PARA ORDENAR
public class CondicionLongevidadComparadora extends Condicion {
	
	//TUVE QUE HACER UNA CONDICION APARTE YA QUE LAS CONDICIONES SE BASAN EN INDICADORES
	//Y LA ANTIGUEDAD DE UNA EMPRESA NO ES UN INDICADOR
	
	public CondicionLongevidadComparadora(String nombre, String indicador, String comparador, String descripcion) {
		super(nombre, null, null, descripcion);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2){
		return empresa1.getAntiguedad() > empresa2.getAntiguedad();		
	}

}
