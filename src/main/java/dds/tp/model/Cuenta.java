package dds.tp.model;

import org.uqbar.commons.utils.Observable;

@Observable
public class Cuenta {

	private String nombre;
	private String empresa;
	private Integer anio;
	private Float valor;

	public Cuenta(String _nombre, String _empresa, Integer _anio, Float _valor) {
		this.nombre = _nombre;
		this.empresa = _empresa;
		this.anio = _anio;
		this.valor = _valor;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public String toString(){
		String line;
		
		line = nombre + "," + empresa + "," + anio.toString() + "," + valor.toString() + "\n";
		
		return line;
	}

	
	
}
