package dds.tp.lexer;

public class Token {
	public boolean esNumero(){
		return false;
	}
	public boolean esOperador(){
		return false;
	}
	public boolean esIdentificador(){
		return false;
	}
	public float getValor(){
		return 0f;
	}
	public float operar(float valor1, float valor2){
		return 0f;
	}
	public String getNombre(){
		return "";
	}
}
