package dds.tp.model;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.periodos.Semestral;

@Observable
public class BalanceSemestral extends Balance {
	
	private Semestral periodo;
	
	public BalanceSemestral(Semestral periodo) {
		super();
		this.periodo = periodo;
	}

	@Override
	public String getPeriodoNombre() {
		return periodo.toString();
	}

	@Override
	public Semestral getPeriodo() {
		return periodo;
	}

	@Override
	public boolean sosAnual() {
		return false;
	}

	@Override
	public boolean sosSemestral() {
		return true;
	}
}
