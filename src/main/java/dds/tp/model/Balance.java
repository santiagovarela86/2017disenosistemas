package dds.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.CuentaNotFound;
import dds.tp.excepciones.ElementoYaExiste;

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
	
	public void setCuentas(List<Cuenta> cuentas){
		this.cuentas = cuentas;
	}
	
	public void addCuenta(Cuenta cta) throws ElementoYaExiste {
		if(!this.contieneCuenta(cta))
			this.cuentas.add(cta);
		else 
			throw new ElementoYaExiste("Ya existe la cuenta " + cta.getNombre() + " dentro del balance " + this.getPeriodo());
	}
	
	public Cuenta getCuenta(String nombre) throws CuentaNotFound {
		try {
			return this.cuentas.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
		}catch (IndexOutOfBoundsException e) {
			throw new CuentaNotFound(nombre, this.getPeriodo());
		}
	}
	
	public boolean contieneCuenta(Cuenta cta) {
		return this.getCuentas().stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(cta.getNombre()));
	}
	
	@Override
	public String toString() {
		return this.periodo;
	}
}
