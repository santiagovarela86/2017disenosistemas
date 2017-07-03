package dds.tp.ui.vm;

import dds.tp.calculador.Expresion;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.Parser;
import dds.tp.parsertools.MyToken;

import java.awt.Color;
import java.util.ArrayList;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

@Observable
public class CargarIndicadoresViewModel {
	
	private Color color;
	private String expresion = "";
	private String resultado = "";
	private String nombreIndicador = "";
	private Boolean habilitado = false;
	private RepositorioIndicadores baulIndicadores;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public CargarIndicadoresViewModel(RepositorioIndicadores indcs) {
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
		try {
			ArrayList<MyToken> operacion = new Parser().parsear(this.getExpresion());
			this.setColor(Color.BLUE);
			resultado = "Indicador guardado con exito";
			baulIndicadores.addIndicador(new Indicador(this.getNombreIndicador(), new Expresion(operacion)));
		}
		catch (ElementoYaExiste e) {
			this.setColor(Color.RED);
			resultado = e.getMessage();
		}
		catch (SintaxisIncorrecta e){
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
