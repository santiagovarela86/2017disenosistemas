package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionCrecienteDecreciente extends Condicion {
	
	private int periodosHaciaAtras;
	private Indicador indicador;
	private Comparador comparador;

	public CondicionCrecienteDecreciente(String nombre,String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
		
	public boolean evaluar(Empresa empresa){
		return false;
	}

}
