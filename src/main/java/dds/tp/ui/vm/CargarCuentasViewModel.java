package dds.tp.ui.vm;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;
import dds.tp.model.LectorCuentas;
import dds.tp.model.repositorios.RepositorioEmpresas;

@Observable
public class CargarCuentasViewModel{ 
	
	private RepositorioEmpresas baulEmpresas;
	private String path;
	private String readFileOK;
	private Boolean habilitado;
	
	public CargarCuentasViewModel(RepositorioEmpresas empresas) {
		this.baulEmpresas = empresas;
	}
	
	public void cargar() {
		try {
			cargarEmpresas(path,Files.lines(Paths.get(path)));
			setReadFileOK("Se cargo el archivo de cuentas con exito");
		} catch (Exception e) {
			  setReadFileOK("Archivo no encontrado/valido");
			  e.printStackTrace();
		}
		
	}
	public void cargarEmpresas(String path, Stream<String> lineas){
		List<Empresa> aIngresar = new LectorCuentas(path).obtenerDatos(lineas);
		this.baulEmpresas.addEmpresas(aIngresar);
		this.baulEmpresas.guardarEmpresas(this.baulEmpresas.getEmpresas());
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
		ObservableUtils.firePropertyChanged(this, "habilitado");
		this.setReadFileOK("");
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
	
	public RepositorioEmpresas getGuardadorEmpresas(){
		return baulEmpresas;
	}
	
}