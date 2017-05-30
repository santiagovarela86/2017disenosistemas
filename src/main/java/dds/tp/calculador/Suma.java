package dds.tp.calculador;

public class Suma implements Operacion {

	private Valor valor;
	
	public Suma(Valor valoraSumar) {
		this.valor = valoraSumar;
	}
	@Override
	public Valor aplicarCon(Valor valorAnterior) {
		return new Numero(valorAnterior.getValor() + valor.getValor());
	}

}
