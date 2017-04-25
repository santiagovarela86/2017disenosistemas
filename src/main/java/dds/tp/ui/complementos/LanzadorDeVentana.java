package dds.tp.ui.complementos;

public class LanzadorDeVentana {
	
	private String nombre;
	private String descripcion;
	private Ventana ventana;
	
	public LanzadorDeVentana(String nombre, String desc, Ventana ventana) {
		this.ventana = ventana;
		this.nombre = nombre;
		this.descripcion = desc;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}
	
	public void abrirVentana() {
		ventana.open();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
}
