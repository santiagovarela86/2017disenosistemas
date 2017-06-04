package dds.tp.lexer;

public class Numero extends Token {
	float valor;
	
	public Numero(String str){
		valor = Float.parseFloat(str);
	}
	
	public float getValor(){
		return valor;
	}
	
	public boolean esNumero(){
		return true;
	}
}
