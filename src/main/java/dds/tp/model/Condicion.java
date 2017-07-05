package dds.tp.model;

public abstract class Condicion {

	private String nombre;
	private String descripcion;
	
	public Condicion(String nombre, String descripcion){
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
