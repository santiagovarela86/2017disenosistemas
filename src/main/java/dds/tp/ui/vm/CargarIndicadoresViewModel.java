package dds.tp.ui.vm;

import dds.tp.lexer.GramaticaParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

@Observable
public class CargarIndicadoresViewModel {
	private String expresion = "";
	private String resultado = "";
	private String nombreIndicador = "";
	private Boolean habilitado = false;

	public void setExpresion(String expresion){
		this.expresion = expresion;
		if (getExpresion() != "" && getNombreIndicador() != ""){
			this.setHabilitado(true);
			ObservableUtils.firePropertyChanged(this, "habilitado");
		}else{
			this.setHabilitado(false);
			ObservableUtils.firePropertyChanged(this, "habilitado");
		}
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
			resultado = "Expresion Cargada con Exito";
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

	public void setNombreIndicador(String nombreIndicador){
		this.nombreIndicador = nombreIndicador;
		if (getExpresion() != "" && getNombreIndicador() != ""){
			this.setHabilitado(true);
			ObservableUtils.firePropertyChanged(this, "habilitado");
		}else{
			this.setHabilitado(false);
			ObservableUtils.firePropertyChanged(this, "habilitado");
		}
	}

	public String getNombreIndicador(){
		return this.nombreIndicador;
	}
	
	public void setHabilitado(Boolean habilitado){
		this.habilitado = habilitado;
	}

	public Boolean getHabilitado(){
		return this.habilitado;
	}
	
}
