package dds.tp.ui.vm.condiciones;

import java.util.List;

import org.uqbar.commons.utils.Observable;

@Observable
public class CargarCondicionSimpleViewModel {
	private String nombreCondicion = "";
	private String descripcion = "";
	private String nombreIndicador = "";
	private List<String> simbolosRelacionales;
	private String simboloRelacional;
	private String valor;
	private String periodosHaciaAtras;
	
	public CargarCondicionSimpleViewModel(){
		
	}
	
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getPeriodosHaciaAtras() {
		return periodosHaciaAtras;
	}

	public void setPeriodosHaciaAtras(String periodosHaciaAtras) {
		this.periodosHaciaAtras = periodosHaciaAtras;
	}

	public void guardarCondicionSimple() {
		// TODO Auto-generated method stub
		
	}
}
