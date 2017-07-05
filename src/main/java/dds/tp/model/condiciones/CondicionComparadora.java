package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

//ESTA SE USA PARA ORDENAR
public class CondicionComparadora extends Condicion {

	private int periodosHaciaAtras;
	
	public CondicionComparadora(String nombre, String indicador, String comparador, String descripcion, int periodosHaciaAtras) {
		super(nombre, indicador, comparador, descripcion);
		this.setPeriodosHaciaAtras(periodosHaciaAtras);
	}
	
	public int getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(int periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
	
	public boolean evaluar(Empresa empresa1, Empresa empresa2, String periodo){
		
		//SEGUN EL COMPARADOR mayor O menor EVALUA SI EL INDICADOR DE LA EMPRESA1 ES CONSISTENTEMENTE MAYOR QUE EL DE LA EMPRESA2
		
		return false;		
	}
}
