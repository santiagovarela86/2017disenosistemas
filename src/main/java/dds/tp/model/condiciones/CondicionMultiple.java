package dds.tp.model.condiciones;

import java.util.List;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

public class CondicionMultiple implements Condicion {

	private String nombre;
	private String descripcion;
	private List<Condicion> condicionesACumplir;
	
	public CondicionMultiple(String nombre, String descripcion, List<Condicion> condicionesACumplir) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.condicionesACumplir = condicionesACumplir;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public void esCumplidaEn(Empresa empresa) {
		for (Condicion condicion : condicionesACumplir) {
			condicion.esCumplidaEn(empresa);
		}
	}
}
