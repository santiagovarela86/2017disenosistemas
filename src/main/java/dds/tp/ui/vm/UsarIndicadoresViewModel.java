package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.evaluador.EvaluadorIndicador;
import dds.tp.lexer.ParseException;
import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

@Observable
public class UsarIndicadoresViewModel {
	
	private GuardadorIndicadores baulIndicadores;
	private List<Empresa> empresas;
	private Indicador indicador;
	private Empresa empresa;
	private Balance balance;
	private Cuenta cuenta;
	
	public UsarIndicadoresViewModel(List<Empresa> empresa, GuardadorIndicadores indicadores) {
		this.baulIndicadores = indicadores;
		this.empresas = empresa;
	}
	
	public List<Indicador> getIndicadores() {
		return baulIndicadores.getIndicadores();
	}
	
	public void setIndicadores(GuardadorIndicadores indicadores) {
		this.baulIndicadores = indicadores;
	}
	
	public List<Empresa> getEmpresas() {
		return this.empresas;
	}
	
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
		if(indicador!=null && balance != null) 
			ObservableUtils.firePropertyChanged(this, "resultado");
	}
	
	public Indicador getIndicador() {
		return indicador;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	} 
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
		this.setBalance(this.getBalances().get(0));
	}
	
	public List<Balance> getBalances() {
		return this.empresa.getBalances();
	}
	
	public Balance getBalance() {
		return balance;
	}
	
	public void setBalance(Balance balance) {
		this.balance = balance;
		if(this.balance!=null)
			ObservableUtils.firePropertyChanged(this, "resultado");
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
	
	public Float getValor() {
		return this.cuenta.getValor();
	}
		
	public String getResultado() {
		try { 
			EvaluadorIndicador ev = new EvaluadorIndicador(indicador, balance, baulIndicadores);
			return ev.evaluar().toString(); 
		}
		catch (IndexOutOfBoundsException e) {
			return "No se encuentra el indicador o la cuenta que utiliza este indicador";
		}
		catch (ParseException ex) {
			//ex.printStackTrace();
			return "Error al parsear la formula";
		}
	}
}
