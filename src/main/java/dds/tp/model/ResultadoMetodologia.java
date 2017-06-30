package dds.tp.model;

public class ResultadoMetodologia {
	private Empresa empresa;
	private String convieneInvertir;
	private String justificacion;
	
	public ResultadoMetodologia(Empresa empresa, String convieneInvertir, String justificacion) {
		super();
		this.empresa = empresa;
		this.convieneInvertir = convieneInvertir;
		this.justificacion = justificacion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public String getConvieneInvertir() {
		return convieneInvertir;
	}

	public String getJustificacion() {
		return justificacion;
	}
}
