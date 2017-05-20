package dds.tp.ui.vm;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;
import dds.tp.model.GuardadorEmpresas;
import dds.tp.model.LectorCuentas;

@Observable
public class CargarCuentasViewModel{ 
	
	private GuardadorEmpresas empresas;
	private String path;
	private String readFileOK;
	private Boolean habilitado;
	
	public CargarCuentasViewModel(GuardadorEmpresas empresas) {
		this.empresas = empresas;
	}
	
	public void cargar() {
		try {
			cargarEmpresas(path,Files.lines(Paths.get(path)));
			setReadFileOK("Se cargo el archivo de cuentas con exito");
			//ACA VER LA FORMA DE BORRAR EL MENSAJE DE QUE SE CARGUE UN ARCHIVO EN LA PANTALLA PRINCIPAL
		} catch (Exception e) {
			  setReadFileOK("Archivo no encontrado/valido");
			  e.printStackTrace();
		}
		
	}
	public void cargarEmpresas(String path, Stream<String> lineas){
		this.empresas.setEmpresas(new LectorCuentas(path).obtenerDatos(lineas));
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
	
	public GuardadorEmpresas getGuardadorEmpresas(){
		return empresas;
	}
	
}