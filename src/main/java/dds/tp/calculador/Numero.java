package dds.tp.calculador;

public class Numero implements Valor {

	private float valor;
	
	public Numero(float valor) {
		super();
		this.valor = valor;
	}
	
	@Override
	public Float getValor() {
		return valor;
	}

}
