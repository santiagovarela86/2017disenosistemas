package dds.tp.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.periodos.Periodo;

@Entity
@Observable
public class Empresa {
	
	@Id
	@GeneratedValue
	private Long id;

	private String nombre;
	private Integer antiguedad;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="empresa_id")
	@Where(clause="tipobalance='balanceSemestral'")
	private List<BalanceSemestral> balancesSemestrales;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="empresa_id")
	@Where(clause="tipobalance='balanceAnual'")
	private List<BalanceAnual> balancesAnuales;
	
	public Empresa() {
		// TODO Auto-generated constructor stub
	}
	
	public Empresa(String nombre, Integer anioFundacion) {
		super();
		this.nombre = nombre;
		this.antiguedad = Year.now().getValue() - anioFundacion; 
		this.balancesAnuales = new ArrayList<>();
		this.balancesSemestrales = new ArrayList<>();
	}

	public Long getId() {
		return id;
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
		if(this.contieneBalance(bal)){
			Balance balanceExistente = this.getBalance(bal.getPeriodoNombre());
			for (Cuenta cta : bal.getCuentas()) {
				if(!balanceExistente.contieneCuenta(cta))
					balanceExistente.addCuenta(cta);
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

	public Balance getBalance(Periodo periodo) {
		return this.getBalance(periodo.toString());
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	public boolean estasGuardada() {
		if(id == null)
			return false;
		else
			return true;
	}
}
