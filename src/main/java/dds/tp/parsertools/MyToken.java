package dds.tp.parsertools;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyToken {
	
	@Id
	@GeneratedValue
	private int id;
	private String contenido;
	private TipoToken tipodetoken;
	
	public MyToken() {
		// TODO Auto-generated constructor stub
	}
	
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
