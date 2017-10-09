package dds.tp.model.metodologia;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;

@Observable
public class ResultadoAnalisis {

	private int puntaje;
	private Empresa empresa;
	private String justificacion;
	
	public ResultadoAnalisis(int puntaje, Empresa empresa, String justificacion) {
		super();
		this.puntaje = puntaje;
		this.empresa = empresa;
		this.justificacion = justificacion;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public String getJustificacion() {
		return justificacion;
	}
	
	public int getPuntaje() {
		return puntaje;
	}

	public int esMayorQue(ResultadoAnalisis resultado2) {
		if(this.puntaje > resultado2.getPuntaje()){
			return -1;
		}else 
			return 1;
	}
	
	public String getNombreEmpresa() {
		return this.empresa.getNombre();
	}
	
	public static ResultadoAnalisis crearResultadoConDatosInsuficientes(Empresa empresa) {
		return new ResultadoAnalisis(0, empresa, "Esta empresa no tiene los elementos suficientes");	
	}
	
	public static ResultadoAnalisis crearResultadoNoCumpleCondiconTaxativa(Empresa empresa) {
		return new ResultadoAnalisis(0, empresa, "Esta empresa no cumple una de las condiciones taxativas");	
	}

}
