package dds.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
@Observable
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
	
	public void setBalances(List<Balance> balances){
		this.balances = balances;
	}
	
	public void addBalance(Balance bal) throws ElementoYaExiste {
		if(!contieneBalance(bal))
			this.balances.add(bal);
		else 
			throw new ElementoYaExiste("El balance ya existe pruebe unir balances");
	}
	
	public void unirBalanceConUnoYaExistente(Balance bal) {
		if(contieneBalance(bal)){
			try {
				Balance balanceExistente = this.getBalance(bal.getPeriodo());
				for (Cuenta cta : bal.getCuentas()) {
					try {
						balanceExistente.addCuenta(cta);
					}catch (ElementoYaExiste e) {
						//No hace nada, ya que existe la cuenta
					}
				}
			}catch (ElementoNotFound e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean contieneBalance(Balance bal) {
		return this.balances.stream().anyMatch(elem -> elem.getPeriodo().equalsIgnoreCase(bal.getPeriodo()));
	}
	
	public Balance getBalance(String periodo) throws ElementoNotFound {
		try {
			return this.balances.stream().filter(elem->elem.getPeriodo().equalsIgnoreCase(periodo)).collect(Collectors.toList()).get(0);
		}catch (IndexOutOfBoundsException e) {
			throw new ElementoNotFound("El balance " + periodo+ " no se ha encontrado");
		}
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
