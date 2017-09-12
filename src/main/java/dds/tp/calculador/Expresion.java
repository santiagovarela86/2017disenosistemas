package dds.tp.calculador;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import dds.tp.model.Balance;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.MyToken;

@Entity
public class Expresion {
	
	@Id
	@GeneratedValue
	private Long id;
	
	//No estoy pudiendo hacer el OneToMany acá tampoco, me dice que no es una colección:
	//Me lo está guardando como TinyBlob... no sé si está bien
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
