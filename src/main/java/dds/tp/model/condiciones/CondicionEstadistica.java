package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionEstadistica extends Condicion {

	private String modo; //promedio, mediana o sumatoria
	
	public CondicionEstadistica(String nombre, String indicador, String comparador, String descripcion, String modo) {
		super(nombre, indicador, comparador, descripcion);
		this.setModo(modo);
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}
	
	public boolean evaluar(Empresa empresa, String modo){
		//ACA IRIA EL CALCULO SEGUN EL MODO //MODELAR LOS MODOS COMO OBJETOS? 
		
		return false;
	}
}
