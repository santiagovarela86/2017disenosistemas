package dds.tp.ui.vm;

import java.awt.Color;
import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.evaluador.Evaluador;
import dds.tp.lexer.Parser;
import dds.tp.model.Balance;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

@Observable
public class UsarIndicadoresViewModel {

	private Evaluador evaluador = new Evaluador();
	private String tipoResultado;
	private Color color;
	private GuardadorIndicadores baulIndicadores;
	private List<Empresa> empresas;
	private Indicador indicador;
	private Empresa empresa;
	private Balance balance;
	private Cuenta cuenta;
	
	private Parser getParser(){
		return this.getEvaluador().getParser();
	}
	
	private Evaluador getEvaluador(){
		return evaluador;
	}
	
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
		return this.indicador.getFormula();
	}
	
	/*
	public String getFormula(){
		return this.indicador.getFormula();
	}
	*/
	
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
		if (this.getParser().esValidoSintacticamente(this.getExpresion())){
			this.setTipoResultado("Resultado");
			this.setColor(Color.BLACK);
			try{
				return String.valueOf(this.getEvaluador().evaluar(this.getParser().parsear(this.getExpresion()), this.getCuentas(), this.getIndicadores()));
			} catch (RuntimeException e){
				this.errorHappen();
				return "El indicador no aplica para esta empresa";
			}
		}else{
			return "Error de Sintaxis";
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
