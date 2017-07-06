package dds.tp.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
@Observable
public class Empresa {

	private String nombre;
	private Integer antiguedad;
	private List<BalanceSemestral> balancesSemestrales;
	private List<BalanceAnual> balancesAnuales;
	
	public Empresa(String nombre, Integer anioFundacion) {
		super();
		this.nombre = nombre;
		this.antiguedad = Year.now().getValue() - anioFundacion; 
		this.balancesAnuales = new ArrayList<>();
		this.balancesSemestrales = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getAntiguedad(){
		return antiguedad;
	}
	
	public List<BalanceAnual> getBalancesAnuales() {
		return balancesAnuales;
	}
	
	public void setBalancesAnuales(List<BalanceAnual> balancesAnuales) {
		this.balancesAnuales = balancesAnuales;
	}
	
	public List<BalanceSemestral> getBalancesSemestrales() {
		return balancesSemestrales;
	}
	
	public void setBalancesSemestrales(List<BalanceSemestral> balancesSemestrales) {
		this.balancesSemestrales = balancesSemestrales;
	}
	
	public List<Balance> getTodosLosBalances() {
		ArrayList<Balance> allBalances =  new ArrayList<Balance>(balancesAnuales);
		allBalances.addAll(balancesSemestrales);
		return allBalances;
	}
	
	public void addBalance(Balance bal) throws ElementoYaExiste {
		if(!contieneBalance(bal))
			if(bal.sosAnual()) {
				this.balancesAnuales.add((BalanceAnual) bal);
			}
			else {
				this.balancesSemestrales.add((BalanceSemestral) bal);
			}
		else 
			throw new ElementoYaExiste("El balance ya existe pruebe unir balances");
	}
	
	public void unirBalanceConUnoYaExistente(Balance bal) {
		if(contieneBalance(bal)){
			try {
				Balance balanceExistente = this.getBalance(bal.getPeriodoNombre());
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
		return this.getTodosLosBalances().stream().anyMatch(elem -> elem.getPeriodo().igualA(bal.getPeriodo()));
	}
	
	public Balance getBalance(String periodo) throws ElementoNotFound {
		try {
			return this.getTodosLosBalances().stream().filter(elem->elem.getPeriodo().igualA(periodo)).collect(Collectors.toList()).get(0);
		}catch (IndexOutOfBoundsException e) {
			throw new ElementoNotFound("El balance " + periodo+ " no se ha encontrado");
		}
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
