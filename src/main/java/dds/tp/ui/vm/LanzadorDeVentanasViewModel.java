package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.LanzadorDeVentana;
import dds.tp.ui.complementos.ViewModel;
@Observable
public class LanzadorDeVentanasViewModel implements ViewModel {
	
	private List<LanzadorDeVentana> todasLasVentanas;
	private LanzadorDeVentana ventanaElegida;
	
	public LanzadorDeVentanasViewModel(List<LanzadorDeVentana> todasLasVentanas) {
		this.todasLasVentanas = todasLasVentanas;
	}
	
	public String getNombre() {
		return this.ventanaElegida.getNombre();
	}
	
	public String getDescripcion() {
		return this.ventanaElegida.getDescripcion();
	}
	
	public void setVentanaElegida(LanzadorDeVentana ventanaElegida) {
		this.ventanaElegida = ventanaElegida;
		ObservableUtils.firePropertyChanged(this, "descripcion");
	}
	
	public LanzadorDeVentana getVentanaElegida() {
		return ventanaElegida;
	}
	
	public List<LanzadorDeVentana> getTodasLasVentanas() {
		return todasLasVentanas;
	}
	
	public void setTodasLasVentanas(List<LanzadorDeVentana> todasLasVentanas) {
		this.todasLasVentanas = todasLasVentanas;
	}
}
