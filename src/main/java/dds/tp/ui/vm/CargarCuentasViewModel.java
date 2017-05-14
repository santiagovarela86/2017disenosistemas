package dds.tp.ui.vm;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.model.GuardadorEmpresas;
import dds.tp.model.IOArchivoDatos;

@Observable
public class CargarCuentasViewModel{ 
	
	private GuardadorEmpresas empresas;
	private String path;
	private String readFileOK;
	private Boolean habilitado;
	
	public CargarCuentasViewModel(GuardadorEmpresas empresas) {
		this.empresas = empresas;
		//Poner absoluth path ak, osea un predeterminado como estabaantes sin queres lo saque
	}
	
	public void cargarCuentas() {
		try {
			Stream<String> lineas = Files.lines(Paths.get(path));
			this.empresas.setEmpresas(new IOArchivoDatos(path).obtenerDatos(lineas));
			setReadFileOK("Se cargó el archivo de cuentas con éxito");
		} catch(NoSuchFileException e){
			  setReadFileOK("Archivo no encontrado");
			  e.printStackTrace();  
		} catch (Exception e) {
			  setReadFileOK("Formato de archivo no válido");
			  e.printStackTrace();
		}	
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
		ObservableUtils.firePropertyChanged(this, "habilitado");
	}

	public String getReadFileOK() {
		return readFileOK;
	}

	public void setReadFileOK(String readFileOK) {
		this.readFileOK = readFileOK;
	}
	
	public Boolean getHabilitado(){
		habilitado = path != null;
		return habilitado;
	}
	
}