package dds.tp.lexer;

public class Identificador extends Token {
	private String nombre;
	
	public Identificador(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public boolean esIdentificador(){
		return true;
	}
}
