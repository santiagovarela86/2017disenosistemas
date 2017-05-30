package dds.tp.calculador;

public class Divicion implements Operacion {
	
	private Valor valor;
	
	public Divicion(Valor valor) {
		this.valor = valor;
	}


	@Override
	public Valor aplicarCon(Valor valorAnterior) {
		// TODO Auto-generated method stub
		return new Numero(valorAnterior.getValor() / valor.getValor());
	}

}
