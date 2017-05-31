package dds.tp.ui.vm;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

@Observable
public class CargarIndicadoresViewModel {
	
	private Color color;
	private String expresion = "";
	private String resultado = "";
	private String nombreIndicador = "";
	private Boolean habilitado = false;
	private GuardadorIndicadores baulIndicadores;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public CargarIndicadoresViewModel(GuardadorIndicadores indcs) {
		this.baulIndicadores = indcs;
	}

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
		InputStream is = new ByteArrayInputStream( this.expresion.getBytes( StandardCharsets.US_ASCII) );
		GramaticaParser parser = new GramaticaParser(is);
		
		try {
			parser.analizarSintacticamente();
			this.setColor(Color.BLUE);
			resultado = "Indicador guardado con exito";
			baulIndicadores.addIndicador(new Indicador(this.getNombreIndicador(), this.getExpresion()));
		}
		catch (ElementoYaExiste e) {
			this.setColor(Color.RED);
			resultado = e.getMessage();
		}
		catch (ParseException e){
			this.setColor(Color.RED);
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
