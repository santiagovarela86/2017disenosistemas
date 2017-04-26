package dds.tp.ui.complementos;

public class OpcionDeAccion {
	
	private String nombre;
	private String descripcion;
	private AccionesDisponibles accionAMostrar;
	
	public OpcionDeAccion(String nombre, String desc, AccionesDisponibles accion) {
		this.accionAMostrar = accion;
		this.nombre = nombre;
		this.descripcion = desc;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public AccionesDisponibles getAccionAMostrar() {
		return accionAMostrar;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
}
