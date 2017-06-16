package dds.tp.calculador;

public class Suma implements Operacion {

	@Override
	public Valor operar(Valor valorIzq, Valor valorDer) {
		return new Numero(valorIzq.getValor()+valorDer.getValor());
	}

	@Override
	public int getPrioridad() {
		return 0;
	}
}
