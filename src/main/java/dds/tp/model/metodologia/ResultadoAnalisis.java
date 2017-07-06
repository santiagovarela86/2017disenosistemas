package dds.tp.model.metodologia;

import dds.tp.model.Empresa;

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

}
