package dds.tp.model.builders;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Condicion;
import dds.tp.model.condiciones.CondicionMultiple;

public class CondicionMultipleBuilder {

	private String nombre;
	private String descripcion;
	private List<Condicion> condicionesACumplir = new ArrayList<>();
	
	public CondicionMultipleBuilder setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public CondicionMultipleBuilder setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public CondicionMultipleBuilder setCondicionesACumplir(List<Condicion> condicionesACumplir) {
		this.condicionesACumplir = condicionesACumplir;
		return this;
	}
	
	public CondicionMultipleBuilder agregarCondicion(Condicion cond) {
		this.condicionesACumplir.add(cond);
		return this;
	}
	
	public CondicionMultiple build() {
		return new CondicionMultiple(nombre, descripcion, condicionesACumplir);
	}
}
