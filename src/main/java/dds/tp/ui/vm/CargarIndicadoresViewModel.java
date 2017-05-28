package dds.tp.ui.vm;

import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.management.RuntimeErrorException;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

@Observable
public class CargarIndicadoresViewModel {
	
	private String expresion = "";
	private String resultado = "";
	private String nombreIndicador = "";
	private Boolean habilitado = false;
	private GuardadorIndicadores indicadores;
	
	public CargarIndicadoresViewModel(GuardadorIndicadores indcs) {
		this.indicadores = indcs;
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
			resultado = "";
			parser.analizarSintacticamente();
			resultado = "Indicador guardado con exito";
			indicadores.addIndicador(new Indicador(this.getNombreIndicador(), this.getExpresion()));	
		}
		catch (RuntimeErrorException e) {
			resultado = e.getTargetError().getMessage();
		}
		catch (ParseException e){
			e.printStackTrace();
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
