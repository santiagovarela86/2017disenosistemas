package dds.tp.calculador;

public class Resta implements Operacion {

	private Valor valoraRestar;
	
	public Resta(Valor valoraRestar) {
		this.valoraRestar = valoraRestar;
	}

	@Override
	public Valor aplicarCon(Valor valorAnterior) {
		return new Numero(valorAnterior.getValor() - valoraRestar.getValor());
	}

}
