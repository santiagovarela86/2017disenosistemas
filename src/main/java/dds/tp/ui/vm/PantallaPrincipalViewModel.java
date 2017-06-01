package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.CargarIndicadoresPredefinidos;
import dds.tp.model.GuardadorEmpresas;
import dds.tp.model.GuardadorIndicadores;

@Observable
public class PantallaPrincipalViewModel{
	
	private String mensajeError;
	
	private GuardadorEmpresas baulEmpresas;
	private GuardadorIndicadores baulIindicadores;

	public PantallaPrincipalViewModel() {
		super();
		this.baulEmpresas = new GuardadorEmpresas();
		this.baulIindicadores = new GuardadorIndicadores();
	}
	
	public GuardadorEmpresas getBaulEmpresas() {
		return baulEmpresas;
	}
	
	public GuardadorIndicadores getBaulIndicadores() {
		return baulIindicadores;
	}
	
	public void cargarPredefinidos(){
		try {
			new CargarIndicadoresPredefinidos().cargar(baulIindicadores);
		} catch (ElementoYaExiste e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
