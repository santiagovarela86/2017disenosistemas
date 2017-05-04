package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.GuardadorEmpresas;

@Observable
public class PantallaPrincipalViewModel{
	
	private GuardadorEmpresas empresas;

	public PantallaPrincipalViewModel() {
		super();
		this.empresas = new GuardadorEmpresas();
	}
	
	public GuardadorEmpresas getEmpresas() {
		return empresas;
	}
	
}
