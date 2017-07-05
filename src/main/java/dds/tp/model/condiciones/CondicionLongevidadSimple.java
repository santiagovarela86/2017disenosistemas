package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;

//ESTA ES LA PARTE TAXATIVA DE LA CONDICION
public class CondicionLongevidadSimple extends Condicion {
	
	//TUVE QUE HACER UNA CONDICION APARTE YA QUE LAS CONDICIONES SE BASAN EN INDICADORES
	//Y LA ANTIGUEDAD DE UNA EMPRESA NO ES UN INDICADOR

	public CondicionLongevidadSimple(String nombre, String indicador, String comparador, String descripcion) {
		super(nombre, null, null, descripcion);
	}

	public boolean evaluar(Empresa empresa){
		return empresa.getAntiguedad() > 10;
	}
	
}
