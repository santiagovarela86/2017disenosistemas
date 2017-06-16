package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.CargarIndicadoresPredefinidos;
import dds.tp.model.RepositorioEmpresas;
import dds.tp.model.RepositorioIndicadores;

@Observable
public class PantallaPrincipalViewModel{
	
	private String mensajeError;
	
	private RepositorioEmpresas baulEmpresas;
	private RepositorioIndicadores baulIindicadores;

	public PantallaPrincipalViewModel() {
		super();
		this.baulEmpresas = new RepositorioEmpresas();
		this.baulIindicadores = new RepositorioIndicadores();
	}
	
	public RepositorioEmpresas getBaulEmpresas() {
		return baulEmpresas;
	}
	
	public RepositorioIndicadores getBaulIndicadores() {
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
