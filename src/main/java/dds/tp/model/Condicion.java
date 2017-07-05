package dds.tp.model;

public class Condicion {
	//public void esCumplidaEn(Empresa empresa);
	//public String getNombre();
	
	private String nombre;
	private String indicador;
	private String comparador; // "mayor" o "menor"
	private String descripcion;
	
	public Condicion(String nombre, String indicador, String comparador, String descripcion){
		this.setNombre(nombre);
		this.setIndicador(indicador);
		this.setComparador(comparador);
		this.setDescripcion(descripcion);
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public String getComparador() {
		return comparador;
	}

	public void setComparador(String comparador) {
		this.comparador = comparador;
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
