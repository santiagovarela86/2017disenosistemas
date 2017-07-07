package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

@Observable
public class PantallaPrincipalViewModel{
	
	private String mensajeError;
	
	private RepositorioEmpresas baulEmpresas;
	private RepositorioIndicadores baulIindicadores;
	private RepositorioMetodologias repoMetodologia;

	public PantallaPrincipalViewModel() {
		super();
		this.baulEmpresas = new RepositorioEmpresas();
		this.baulIindicadores = new RepositorioIndicadores();
		this.baulIindicadores.cargarPredeterminados();
		this.repoMetodologia = new RepositorioMetodologias();
		this.repoMetodologia.cargarPredeterminados(baulIindicadores);
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
	
	public RepositorioMetodologias getRepoMetodologia() {
		return repoMetodologia;
	}
}
