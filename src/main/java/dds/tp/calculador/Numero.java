package dds.tp.calculador;

public class Numero implements Valor {

	private Double valor;
	
	public Numero(Double valor) {
		super();
		this.valor = valor;
	}
	
	@Override
	public Double getValor() {
		return valor;
	}

}
