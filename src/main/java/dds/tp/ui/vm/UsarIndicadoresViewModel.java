package dds.tp.ui.vm;

import java.awt.Color;
import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.evaluador.EvaluadorIndicador;
import dds.tp.excepciones.CuentaNotFound;
import dds.tp.excepciones.IndicadorNotFound;
import dds.tp.lexer.ParseException;
import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

@Observable
public class UsarIndicadoresViewModel {
	
	private String tipoResultado;
	private Color color;
	private GuardadorIndicadores baulIndicadores;
	private List<Empresa> empresas;
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
		this.empresa = empresa;
		this.setBalance(this.getBalances().get(0));
		if(this.empresa!=null) {
			ObservableUtils.firePropertyChanged(this, "infoEmpresa");
		}
	}
	
	public List<Balance> getBalances() {
		return this.empresa.getBalances();
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
	
	public Float getValor() {
		return this.cuenta.getValor();
	}
		
	public String getResultado() {
		try { 
			EvaluadorIndicador ev = new EvaluadorIndicador(indicador, balance, baulIndicadores);
			this.setTipoResultado("Resultado");
			this.setColor(Color.BLACK);
			return ev.evaluar().toString(); 
		}
		catch (CuentaNotFound ex) {
			this.errorHappen();
			return ex.getMessage();
		}
		catch (IndicadorNotFound ex) {
			this.errorHappen();
			return ex.getMessage();
		}
		catch (ParseException ex) {
			return "Error al parsear la formula";
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
