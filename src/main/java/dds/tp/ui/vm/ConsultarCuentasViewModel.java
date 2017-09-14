	package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioEmpresas;

@Observable
public class ConsultarCuentasViewModel{

	private RepositorioEmpresas repoEmpresas;
	private Empresa empresa;
	private Balance balance;
	private Cuenta cuenta;
	
	public ConsultarCuentasViewModel(RepositorioEmpresas empresas) {
		this.repoEmpresas = empresas;
	}
	
	public List<Empresa> getEmpresas() {
		return this.repoEmpresas.getEmpresas();
	}
	
	public Empresa getEmpresa() {
		return empresa;
	} 
	
	public void setEmpresa(Empresa empresa) {
		repoEmpresas.inicializarBalances(empresa);
		this.empresa = empresa;
		this.setBalance(this.getBalances().get(0));
	}
	
	public List<Balance> getBalances() {
		return this.empresa.getTodosLosBalances();
	}
	
	public Balance getBalance() {
		return balance;
	}
	
	public void setBalance(Balance balance) {
		this.balance = balance;
	}
	
	public List<Cuenta> getCuentas() {
		return this.balance.getCuentas();
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	public String getNombre() {
		return this.cuenta.getNombre();
	}
	
	public Double getValor() {
		return this.cuenta.getValor();
	}
	
}
