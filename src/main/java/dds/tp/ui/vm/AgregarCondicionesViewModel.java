package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class AgregarCondicionesViewModel {

	private MetodologiaBuilder metodologiaBuilder;
	private RepositorioIndicadores repoIndicadores;
	private String mensajeOK;
	
	public AgregarCondicionesViewModel(MetodologiaBuilder metodologiaBuilder, RepositorioIndicadores repoIndicadores) {
		super();
		this.metodologiaBuilder = metodologiaBuilder;
		this.repoIndicadores = repoIndicadores;
	}

	public MetodologiaBuilder getMetodologiaBuilder() {
		return metodologiaBuilder;
	}

	public RepositorioIndicadores getRepoIndicadores() {
		return repoIndicadores;
	}

	public String getMensajeOK() {
		return mensajeOK;
	}

	public void setMensajeOK(String mensajeOK) {
		this.mensajeOK = mensajeOK;
	}

}
