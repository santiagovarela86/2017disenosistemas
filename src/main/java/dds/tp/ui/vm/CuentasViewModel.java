package dds.tp.ui.vm;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Cuenta;
import dds.tp.ui.complementos.ViewModel;

@Observable
public class CuentasViewModel implements ViewModel {
	private Cuenta cuenta;
	
	public CuentasViewModel(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	public String getNombre() {
		return cuenta.getNombre();
	}
	
	public Integer getAnio() {
		return cuenta.getAnio();
	}
	
	public String getEmpresa(){
		return cuenta.getEmpresa();
	}
	
	public Float valor(){
		return cuenta.getValor();
	}
}
