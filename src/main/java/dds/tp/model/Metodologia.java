package dds.tp.model;

import java.util.ArrayList;

import dds.tp.excepciones.CondicionNoCumplida;

public class Metodologia {

	private String nombre;
	private ArrayList<Condicion> condiciones;
	
	public Metodologia(String nombre) {
		this.nombre = nombre;
		this.condiciones = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void agregarCondicion(Condicion cond) {
		this.condiciones.add(cond);
	}
	
	public ResultadoMetodologia evaluarEn(Empresa empresa){
		try {
			for (Condicion condicion : condiciones) {
				condicion.esCumplidaEn(empresa);
			}
			return new ResultadoMetodologia(empresa, "Si", "Se cumplen todas las condiciones");
		}
		catch(CondicionNoCumplida ex){
			return new ResultadoMetodologia(empresa, "No", ex.getMessage());
		}
	}
}
