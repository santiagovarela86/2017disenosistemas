package dds.tp.ui.vm;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;
import dds.tp.model.IOArchivoCuentas;

@Observable
public class CargarCuentasViewModel implements ViewModel{ 
	
	private IOArchivoCuentas lector;
	private Boolean habilitado;
	private String readFileOK;
	
	public CargarCuentasViewModel(IOArchivoCuentas _lector) {
		lector = _lector;
		this.habilitado = false;
	}
	
	public void cargarCuentas(){
		lector.cargarCuentas();
		setReadFileOK("ya fue cargado con exito");
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

	public String getReadFileOK() {
		return readFileOK;
	}

	public void setReadFileOK(String readFileOK) {
		this.readFileOK = readFileOK;
	}
	
	
	
}