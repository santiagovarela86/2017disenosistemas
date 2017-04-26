package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;

import dds.tp.model.Cuenta;
import dds.tp.model.IOArchivoCuentas;

@Observable
public class CargarCuentasViewModel implements ViewModel{ 
	IOArchivoCuentas lector;
	private Boolean habilitado = false;
	private String path;
	
	public CargarCuentasViewModel(IOArchivoCuentas _lector) {
		lector = _lector;
	}
	
	public void cargarCuentas(){
		lector.cargarCuentas();
	}
	
	public Boolean getHabilitado(){
		return habilitado;
	}
	
	public String getPath(){
		path = lector.getPath();
		return path;
	}
}