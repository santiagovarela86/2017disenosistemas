package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.GuardadorEmpresas;
import dds.tp.model.GuardadorIndicadores;

@Observable
public class PantallaPrincipalViewModel{
	
	private String archivoCuentasOK;
	
	private GuardadorEmpresas empresas;
	private GuardadorIndicadores indicadores;

	public PantallaPrincipalViewModel() {
		super();
		this.empresas = new GuardadorEmpresas();
		this.indicadores = new GuardadorIndicadores();
	}
	
	public GuardadorEmpresas getEmpresas() {
		return empresas;
	}
	
	public GuardadorIndicadores getIndicadores() {
		return indicadores;
	}
	
	public String getArchivoCuentasOK() {
		return archivoCuentasOK;
	}

	public void setArchivoCuentasOK(String archivoCuentasOK) {
		this.archivoCuentasOK = archivoCuentasOK;
	}
}
