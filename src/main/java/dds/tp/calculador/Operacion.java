package dds.tp.calculador;

public interface Operacion {
	public Valor operar(Valor valorIzq, Valor valorDer);
	public int getPrioridad();
}
