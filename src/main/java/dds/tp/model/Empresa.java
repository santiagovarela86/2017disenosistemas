package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String nombre;
	private List<Balance> balances;
	
	public Empresa(String nombre) {
		super();
		this.nombre = nombre;
		this.balances = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public List<Balance> getBalances() {
		return balances;
	}
	
	public void addBalance(Balance bal) {
		this.balances.add(bal);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
