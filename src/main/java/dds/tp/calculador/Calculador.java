package dds.tp.calculador;

public class Calculador {
	
	private Termino termino;
	
	public Calculador(Termino term) {
		this.termino = term;
	}
	
	public float obtenerResultado() {
		return termino.getValor();
	}

}
