package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.GuardadorEmpresas;
import dds.tp.model.IOArchivoDatos;

@Observable
public class CargarCuentasViewModel{ 
	
	private GuardadorEmpresas empresas;
	private String path;
	private String readFileOK;
	
	public CargarCuentasViewModel(GuardadorEmpresas empresas) {
		this.empresas = empresas;
		//Poner absoluth path ak, osea un predeterminado como estabaantes sin queres lo saque
	}
	
	public void cargarCuentas(){
		try {
			this.empresas.setEmpresas(new IOArchivoDatos(path).obtenerDatos());
			setReadFileOK("Se cargó el archivo de cuentas con éxito");
		} catch (NullPointerException e) {
			setReadFileOK("Por favor elija un archivo");
		}
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
	}

	public String getReadFileOK() {
		return readFileOK;
	}

	public void setReadFileOK(String readFileOK) {
		this.readFileOK = readFileOK;
	}
	
	
	
}