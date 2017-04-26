package dds.tp.ui.vm;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;

import dds.tp.model.Cuenta;
import dds.tp.model.IOArchivoCuentas;

@Observable
public class ConsultarCuentasViewModel implements ViewModel{
	IOArchivoCuentas lector;
	private ArrayList<Cuenta> cuentas;
	
	public ConsultarCuentasViewModel(IOArchivoCuentas _lector){
		lector = _lector;
	}
	
	public void consultarCuentas(){
		lector.consultarCuentas();
	}
	
	public ArrayList<Cuenta> getCuentas(){
		cuentas = lector.getCuentas();
		return cuentas;
	}
	
}