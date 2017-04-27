package dds.tp.ui.vm;

import java.io.File;
import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.model.IOArchivoCuentas;
import dds.tp.ui.complementos.OpcionDeAccion;
import dds.tp.ui.complementos.ViewModel;
@Observable
public class PantallaPrincipalViewModel implements ViewModel {
	
	private File miArchivo = new File("cuentas.txt");
	private IOArchivoCuentas lector = new IOArchivoCuentas(miArchivo.getAbsolutePath());
		
	private List<OpcionDeAccion> todasLasOpcionesDeAccion;
	private OpcionDeAccion ventanaElegida;
	
	public PantallaPrincipalViewModel(List<OpcionDeAccion> todasLasOpciones) {
		this.todasLasOpcionesDeAccion = todasLasOpciones;
	}
	
	public String getNombre() {
		return this.ventanaElegida.getNombre();
	}
	
	public String getDescripcion() {
		return this.ventanaElegida.getDescripcion();
	}
	
	public void setVentanaElegida(OpcionDeAccion ventanaElegida) {
		this.ventanaElegida = ventanaElegida;
		ObservableUtils.firePropertyChanged(this, "descripcion");
	}
	
	public OpcionDeAccion getVentanaElegida() {
		return ventanaElegida;
	}
	
	public List<OpcionDeAccion> getTodasLasVentanas() {
		return todasLasOpcionesDeAccion;
	}
	
	public void setTodasLasVentanas(List<OpcionDeAccion> todasLasOpcionesDeAccion) {
		this.todasLasOpcionesDeAccion = todasLasOpcionesDeAccion;
	}
	
	public IOArchivoCuentas getLector(){
		return lector;
	}
}
