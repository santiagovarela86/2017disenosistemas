package dds.tp.ui.vm;

import dds.tp.lexer.GramaticaParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.uqbar.commons.utils.Observable;

@Observable
public class CargarIndicadoresDefinidosXUsuarioViewModel {
	private String expresion;
	private String resultado = "";
	
	public void setExpresion(String expresion){
		this.expresion = expresion;
	}
	
	public String getExpresion(){
		return this.expresion;
	}
	
	public void parsearExpresion(){
		InputStream is = new ByteArrayInputStream( this.expresion.getBytes( Charset.defaultCharset() ) );
		GramaticaParser parser = new GramaticaParser(is);
		
		try {
			resultado = "";
			parser.Input();
		} catch (Exception e){
			resultado = "Error de expresion";
		}
	}
	
	public void setResultado(String resultado){
		this.resultado = resultado;
	}
	
	public String getResultado(){
		return this.resultado;
	}
}
