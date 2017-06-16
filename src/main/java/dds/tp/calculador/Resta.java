package dds.tp.calculador;

public class Resta implements Operacion {

	@Override
	public Valor operar(Valor valorDer, Valor valorIzq) {
		return new Numero(valorIzq.getValor()-valorDer.getValor());
	}

	@Override
	public int getPrioridad() {
		return 0;
	}


}
