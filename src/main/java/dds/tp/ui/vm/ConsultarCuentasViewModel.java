package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.ui.complementos.ViewModel;

@Observable
public class ConsultarCuentasViewModel implements ViewModel{
	
	private List<CuentasViewModel> cuentas;
	
	public ConsultarCuentasViewModel(List<CuentasViewModel> cuentas) {
		this.cuentas = cuentas;
	}
	
	public List<CuentasViewModel> getCuentas() {
		return cuentas;
	}
}
