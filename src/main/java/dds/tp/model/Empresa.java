package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String nombre;
	private List<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	
	public void setCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
	}
	
	
	
	
	
}
