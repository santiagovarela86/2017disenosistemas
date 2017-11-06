package dds.tp.calculador;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import dds.tp.model.Balance;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.MyToken;

@Entity
public class Expresion {
	
	@Id
	@GeneratedValue
	int id;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderColumn(name="ordenEnLaExpresion")
	@JoinColumn(name="expresion_id")
	private List<MyToken> expresionTokeniseada;

	public Expresion() {
		// TODO Auto-generated constructor stub
	}
	
	public Expresion(List<MyToken> expresionTokeniseada) {
		this.expresionTokeniseada = expresionTokeniseada;
	}
	
	public List<MyToken> getExpresionTokeniseada() {
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
	
	public Double calculateCon(Balance balance, RepositorioIndicadores baulIndicadores, Usuario usuario) {
		return new Calculador().calcularExpresion(this, balance, baulIndicadores, usuario);
	}

}
