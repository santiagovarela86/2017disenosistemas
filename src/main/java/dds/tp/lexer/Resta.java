package dds.tp.lexer;

public class Resta extends Token {
	public float operar(float valor1, float valor2){
		return valor1 - valor2;
	}
	
	public boolean esOperador(){
		return true;
	}
}
