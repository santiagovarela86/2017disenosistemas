package dds.tp.calculador;

import java.util.ArrayList;

import dds.tp.model.Balance;
import dds.tp.model.RepositorioIndicadores;
import dds.tp.parsertools.MyToken;

public class Expresion {

	private ArrayList<MyToken> expresionTokeniseada;

	public Expresion(ArrayList<MyToken> expresionTokeniseada) {
		this.expresionTokeniseada = expresionTokeniseada;
	}
	
	public ArrayList<MyToken> getExpresionTokeniseada() {
		return expresionTokeniseada;
	}
	
	@Override
	public String toString() {
		String valor = "";
		for (MyToken myToken : expresionTokeniseada) {
			valor = valor+myToken.getContenido();
		}
		return valor;
	}
	
	public Double calculateCon(Balance balance, RepositorioIndicadores baulIndicadores) {
		return new Calculador().calcularExpresion(this, balance, baulIndicadores);
	}
	

}
