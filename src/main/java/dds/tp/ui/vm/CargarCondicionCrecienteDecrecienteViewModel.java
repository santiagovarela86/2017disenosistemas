package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

@Observable
public class CargarCondicionCrecienteDecrecienteViewModel {
	private String nombreCondicion = "";
	private String descripcion = "";
	private String nombreIndicador = "";
	private List<String> simbolosRelacionales;
	private String simboloRelacional;
	private String periodosHaciaAtras;
	
	public String getNombreCondicion() {
		return nombreCondicion;
	}
	
	public void setNombreCondicion(String nombreCondicion) {
		this.nombreCondicion = nombreCondicion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public List<String> getSimbolosRelacionales() {
		return simbolosRelacionales;
	}

	public void setSimbolosRelacionales(List<String> simbolosRelacionales) {
		this.simbolosRelacionales = simbolosRelacionales;
	}

	public String getSimboloRelacional() {
		return simboloRelacional;
	}

	public void setSimboloRelacional(String simboloRelacional) {
		this.simboloRelacional = simboloRelacional;
	}

	public String getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(String periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
	
	public void guardarCondicionCrecienteDecreciente() {
		// TODO Auto-generated method stub
		
	}

}
