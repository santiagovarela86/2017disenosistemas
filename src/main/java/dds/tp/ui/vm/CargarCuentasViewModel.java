package dds.tp.ui.vm;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;
import dds.tp.model.IOArchivoCuentas;

@Observable
public class CargarCuentasViewModel implements ViewModel{ 
	
	private IOArchivoCuentas lector;
	private Boolean habilitado;
	
	public CargarCuentasViewModel(IOArchivoCuentas _lector) {
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
		return lector.getPath();
	}
	
	public void setPath(String path){
		this.lector.setPath(path);
		ObservableUtils.firePropertyChanged(this, "path");
	}
	
}