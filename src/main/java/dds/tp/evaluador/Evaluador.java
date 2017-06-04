package dds.tp.evaluador;

import java.util.LinkedList;
import java.util.List;
import dds.tp.lexer.*;
import dds.tp.model.Cuenta;
import dds.tp.model.Indicador;

import java.util.Optional;
import java.util.Stack;

public class Evaluador {
	private Stack<Float> stack = new Stack<Float>();
	private Parser parser = new Parser();
	
	public float evaluar(List<String> notacionPolacaReversa, List<Cuenta> cuentas, List<Indicador> indicadores) throws RuntimeException {
		
		for (Token token : this.convertirAToken(notacionPolacaReversa)){
			if (token.esNumero()){
				stack.push(token.getValor());
			}else if (token.esOperador()){
				if (stack.size() < 2) {
					throw new RuntimeException("Error Sintáctico");
				} else {
					stack.push(token.operar(stack.pop(), stack.pop()));
				}
			}else if (token.esIdentificador()){
				stack.push(this.resolver(token, cuentas, indicadores));
			}
		}
		return stack.pop();
	}
	
	public float resolver(Token token, List<Cuenta> cuentas, List<Indicador> indicadores){
		if (_esCuenta(token, cuentas)){
			Optional<Cuenta> cuenta = cuentas.stream().filter(c -> c.getNombre().equalsIgnoreCase(token.getNombre())).findFirst();
			return cuenta.get().getValor();
		}else if (_esIndicador(token, indicadores)){
			Optional<Indicador> indicador = indicadores.stream().filter(i -> i.getNombre().equalsIgnoreCase(token.getNombre())).findFirst();
			return this.evaluar(parser.parsear(indicador.get().getFormula()), cuentas, indicadores);
		}else throw new RuntimeException();
	}
	
	public boolean _esCuenta(Token token, List<Cuenta> cuentas){
		return cuentas.stream().anyMatch(cuenta -> cuenta.getNombre().equalsIgnoreCase(token.getNombre()));
	}
	
	public boolean _esIndicador(Token token, List<Indicador> indicadores){
		return indicadores.stream().anyMatch(indicador -> indicador.getNombre().equalsIgnoreCase(token.getNombre()));
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
	
	public List<Token> convertirAToken(List<String> strings){
		List<Token> tokens = new LinkedList<Token>();
		
		for (String str : strings){
			tokens.add(this.aToken(str));
		}
		
		return tokens;
	}
	
	public Parser getParser(){
		return parser;
	}
	
	public Token aToken(String str){
		if (_esNumero(str)){
			return new Numero(str);
		} else if (_esOperador(str)){
			switch (str){
			case "+":
				return new Suma();
			case "-":
				return new Resta();
			case "/":
				return new Division();
			case "*":
				return new Multiplicacion();
			}
		} else if (_esIdentificador(str)){
			return new Identificador(str);
		}
		
		return null;
	}
}
