package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class PantallaPrincipalViewModel{
	
	private String mensajeError;
	
	private RepositorioEmpresas baulEmpresas;
	private RepositorioIndicadores baulIindicadores;

	public PantallaPrincipalViewModel() {
		super();
		this.baulEmpresas = new RepositorioEmpresas();
		this.baulIindicadores = new RepositorioIndicadores();
		this.baulIindicadores.cargarPredeterminados();
	}
	
	public RepositorioEmpresas getBaulEmpresas() {
		return baulEmpresas;
	}
	
	public RepositorioIndicadores getBaulIndicadores() {
		return baulIindicadores;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}
