package dds.tp.calculador;

import java.util.Stack;

import dds.tp.model.Balance;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.parsertools.MyToken;

public class Calculador {
	Stack<Operacion> operaciones;
	Stack<Valor> valores;
	
	public Calculador() {
		operaciones = new Stack<>();
		valores = new Stack<>();
	}
	
	public Double calcularExpresion(Expresion expresion,Balance balance, RepositorioIndicadores baulIndicadores) {
		for (MyToken token : expresion.getExpresionTokeniseada()) {
			switch (token.getTipodetoken()) {
			case NUMERO:
				valores.push(new Numero(Double.parseDouble(token.getContenido())));
				break;
			case IDENTIFICADOR:
				valores.push(new Identificador(token.getContenido()).buscaTuValorEn(balance, baulIndicadores));
				break;
			case SUMA:
				this.agregarOperacion(new Suma());
				break;
			case DIVISION:
				this.agregarOperacion(new Division());
				break;
			case MULTIPLICACION:
				this.agregarOperacion(new Multiplicacion());
				break;
			case RESTA:
				this.agregarOperacion(new Resta());
				break;
			default:
				break;
			}
		}
		while(!operaciones.isEmpty()) {
			this.operar();
		}
		return valores.pop().getValor();
	}
	
	private void agregarOperacion(Operacion op) {
		//Se puede generar una abstraccion que sea op.tieneMayorPrioridadQue(Operacion)
		//en vez de operaciones.peek().getPrioridad() <= op.getPrioridad()
		while(!operaciones.isEmpty() && !(operaciones.peek().getPrioridad() <= op.getPrioridad())) {
			this.operar();
		}
		operaciones.push(op);
	}
	
	private void operar() {
		Valor resultado = operaciones.pop().operar(valores.pop(), valores.pop());
		valores.push(resultado);
	}
}
