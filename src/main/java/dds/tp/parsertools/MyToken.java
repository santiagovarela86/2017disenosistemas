package dds.tp.parsertools;

public class MyToken {
	
	private String contenido;
	private TipoToken tipodetoken;
	
	public MyToken(String contenido, TipoToken tipo) {
		this.contenido = contenido;
		this.tipodetoken = tipo;
	}

	public String getContenido() {
		return contenido;
	}

	public TipoToken getTipodetoken() {
		return tipodetoken;
	}
}
