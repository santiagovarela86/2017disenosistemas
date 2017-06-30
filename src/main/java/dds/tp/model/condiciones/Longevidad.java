package dds.tp.model.condiciones;

import dds.tp.excepciones.CondicionNoCumplida;
import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

public class Longevidad implements Condicion {
	
	private int edadMinima;
	private String nombre = "Longevidad";
	private String descripcion = "Esta condicion se cumple cuando una empresa tiene mas años que la edad minima";
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Longevidad() {
	}
	
	//Hago private para que al modificar la edadMinima sea una nueva condicion
	private Longevidad(int edadMinima) {
		super();
		this.edadMinima = edadMinima;
	}

	public Longevidad setEdad(int edadMinima) {
		return new Longevidad(edadMinima);
	}
	
	@Override
	public void esCumplidaEn(Empresa empresa) {	
		if(empresa.getEdad() < edadMinima) {
			throw new CondicionNoCumplida("Esta empresa no tiene al menos " + edadMinima + " años");
		}
	}

}
