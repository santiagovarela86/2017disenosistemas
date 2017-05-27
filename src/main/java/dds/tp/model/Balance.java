package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;

@Observable
public class Balance {
	private String periodo;
	private List<Cuenta> cuentas;
	
	public Balance(String periodo) {
		super();
		this.periodo = periodo;
		this.cuentas = new ArrayList<>();
	}

	public String getPeriodo() {
		return periodo;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public void addCuenta(Cuenta cta) {
		this.cuentas.add(cta);
	}
	
	@Override
	public String toString() {
		return this.periodo;
	}
	
	public void setCuentas(List<Cuenta> cuentas){
		this.cuentas = cuentas;
	}
	
}
