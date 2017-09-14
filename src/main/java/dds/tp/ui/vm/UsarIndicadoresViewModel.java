package dds.tp.ui.vm;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Observable
public class UsarIndicadoresViewModel {
	
	private String tipoResultado;
	private Color color;
	private RepositorioIndicadores baulIndicadores;
	private RepositorioEmpresas repoEmpresas;
	private Indicador indicador;
	private Empresa empresa;
	private Balance balance;
	private Cuenta cuenta;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setTipoResultado(String tipoResultado) {
		this.tipoResultado = tipoResultado;
	}
	
	public String getTipoResultado() {
		return tipoResultado;
	}
	
	public String getExpresion() {
		return "Formula: " + this.indicador.getFormula();
	}
	
	public UsarIndicadoresViewModel(RepositorioEmpresas empresa, RepositorioIndicadores indicadores) {
		this.baulIndicadores = indicadores;
		this.repoEmpresas = empresa;
	}
	
	public List<Indicador> getIndicadores() {
		return baulIndicadores.getIndicadores();
	}
	
	public void setIndicadores(RepositorioIndicadores indicadores) {
		this.baulIndicadores = indicadores;
	}
	
	public List<Empresa> getEmpresas() {
		return this.repoEmpresas.getEmpresas();
	}
	
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
		if(indicador!=null && balance != null) {
			ObservableUtils.firePropertyChanged(this, "resultado");
			ObservableUtils.firePropertyChanged(this, "expresion");
		}
	}
	
	public Indicador getIndicador() {
		return indicador;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	} 
	
	public void setEmpresa(Empresa empresa) {
		repoEmpresas.inicializarBalances(empresa);
		this.empresa = empresa;
		this.setBalance(this.getBalances().get(0));
		if(this.empresa!=null) {
			ObservableUtils.firePropertyChanged(this, "infoEmpresa");
		}
	}
	
	public List<Balance> getBalances() {
		return this.empresa.getTodosLosBalances();
	}
	
	public Balance getBalance() {
		return balance;
	}
	
	public void setBalance(Balance balance) {
		this.balance = balance;
		if(this.balance!=null) {
			ObservableUtils.firePropertyChanged(this, "resultado");
			ObservableUtils.firePropertyChanged(this, "infoEmpresa");
		}
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
		
	public String getResultado() {
		try { 
			Double resultado = this.indicador.evaluar(this.balance, this.baulIndicadores);	
			this.setTipoResultado("Resultado");
			this.setColor(Color.BLACK);
			return new DecimalFormat("#.####").format(resultado); 
		}
		catch (ElementoNotFound ex) {
			this.errorHappen();
			return ex.getMessage();
		}
	}
	
	private void errorHappen(){
		this.setTipoResultado("Error");
		this.setColor(Color.RED);
	}
	
	public String infoEmpresa() {
		return "Empresa " + this.empresa.getNombre() + ". Balance de " + this.getBalance().getPeriodo();
	}
	
}
