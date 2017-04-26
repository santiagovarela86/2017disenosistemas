package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;
import dds.tp.model.IOArchivoCuentas;

@Observable
public class IOArchivoCuentasViewModel implements ViewModel{ 
	
	private IOArchivoCuentas lector;
	private Boolean habilitado;
	private String path;
	
	public IOArchivoCuentasViewModel(IOArchivoCuentas _lector) {
		lector = _lector;
		this.habilitado = false;
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