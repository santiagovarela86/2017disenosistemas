package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionCrecienteDecreciente extends Condicion {
	
	private int periodosHaciaAtras;

	public CondicionCrecienteDecreciente(String nombre, String indicador, String comparador, String descripcion, int periodosHaciaAtras) {
		super(nombre, indicador, comparador, descripcion);
		this.setPeriodosHaciaAtras(periodosHaciaAtras);
	}
	
	public int getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(int periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
		
	public boolean evaluar(Empresa empresa){
		//ESTO DICE SI EL INDICADOR ES SIEMPRE O CASI SIEMPRE 
		//CRECIENTE O DECRECIENTE EN ESE PERIODO
		//(ESTABLECER LIMITE, LA MITAD MAS UNO?, 75%?)
		
		return false;
	}

}
