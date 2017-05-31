package dds.tp.calculador;

public class Multiplicacion implements Operacion {

	private Valor valor;
	 
	public Multiplicacion(Valor valoraMultiplicar) {
		this.valor = valoraMultiplicar;
	}

	@Override
	public Valor aplicarCon(Valor valorAnterior) {
		return new Numero(valorAnterior.getValor() * this.valor.getValor());
	}

}
