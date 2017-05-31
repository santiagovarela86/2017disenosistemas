package dds.tp.calculador;

import java.util.ArrayList;

public class Termino implements Valor {
	
	private Valor valorFinal;
	private Valor valorInicial;
	private ArrayList<Operacion> operaciones;
	
	public Termino(Valor valorInicial) {
		super();
		this.valorInicial = valorInicial;
		this.operaciones = new ArrayList<>();
	}

	public Float getValor() {
		valorFinal = valorInicial;
		for (Operacion op : this.operaciones) {
			valorFinal = op.aplicarCon(valorFinal);
		}
		return valorFinal.getValor();
	}

	public void addOperacion(Operacion op){
		operaciones.add(op);
	}
	

}
