package dds.tp.ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Condicion;

@Observable
public class CrearMetodologiaViewModel {

	private String nombreMetodologia = "";
	private List<Condicion> condiciones = new ArrayList<Condicion>();
	private Condicion condicion;
	
	public String getNombreMetodologia() {
		return nombreMetodologia;
	}
	
	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}
	
	public List<Condicion> getCondiciones() {
		return condiciones;
	}
	
	public String getNombreCondicion() {
		return condicion.getNombre();
	}
	
	public String getDescripcionCondicion() {
		return condicion.getDescripcion();
	}
	
	public Condicion getCondicion() {
		return condicion;
	}
	
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	
}
