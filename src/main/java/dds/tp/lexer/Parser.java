package dds.tp.lexer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Parser {
	public List<String> parsear(String expresionLiteral){
		Queue<String> output = new LinkedList<String>();
		Stack<String> operadores = new Stack<String>();
		List<String> notacionPolacaReversa = new LinkedList<String>();
		
		//SI ES VALIDO SINTACTICAMENTE
		if (this.esValidoSintacticamente(expresionLiteral)){
			// TOKENIZO
			//String[] tokens = this.sinEspacios(expresionLiteral).split("(?<=[-+*/()])|(?=[-+*/()])");
			String[] tokens = expresionLiteral.split(" ");
			
			// SHUNTING YARD ALGORITHM
			for (int i = 0; i < tokens.length; i++) {
				String tokenActual = tokens[i];

				if (_esIdentificador(tokenActual) || _esNumero(tokenActual)) {
					output.add(tokenActual);
				} else if (_esOperador(tokenActual)) {
					if (operadores.isEmpty()) {
						operadores.push(tokenActual);
					} else {
						if (this.precedencia(tokenActual) >= this.precedencia(operadores.peek())) {
							operadores.push(tokenActual);
						} else {
							while (!operadores.isEmpty()) {
								output.add(operadores.pop());
							}
							operadores.push(tokenActual);
						}
					}
				}
			}

			// ULTIMA PARTE ALGORITMO SHUNTING YARD
			while (!operadores.isEmpty()) {
				output.add(operadores.pop());
			}

			// A ESTA ALTURA EL OUTPUT DEBERIA ESTAR ORDENADO DE FORMA POLACA REVERSA
			for (String queueElement : output) {
				//System.out.println(queueElement);
				notacionPolacaReversa.add(queueElement);
			}
		} else {
			; //ERROR SINTACTICO
			throw new RuntimeException("Error de Sintaxis");
		}
		
		return notacionPolacaReversa;
	}
	
	public boolean _esIdentificador(String str){
		return str.matches("[a-zA-Z]+");
	}

	public boolean _esOperador(String str){
		return str.matches("[+-/*]");
	}
	
	public boolean _esNumero(String str){
		return str.matches("[0-9]+");
	}
	
	public int precedencia(String str){
		int result = 0;
		switch (str){
		case "*": 
			result = 2;
			break;
		case "/": 
			result = 2;
			break;
		case "+": 
			result = 1;
			break;
		case "-": 
			result = 1;
			break;
		}
		return result;
	}
	
	public boolean esValidoSintacticamente(String str){
		//NO SE ACEPTAN IDENTIFICADORES QUE CONTENGAN NUMEROS, O ES UN NUMERO O ES UN IDENTIFICADOR
		return str.matches("^(([0-9 ]+)|([a-zA-Z ])+)([+-/* ]+(([0-9 ]+)|([a-zA-Z ])+))*");
	}
}
