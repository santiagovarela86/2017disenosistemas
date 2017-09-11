package dds.tp.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.periodos.Anual;

@Entity
@Observable
public class BalanceAnual extends Balance {

	@OneToOne
	private Anual periodo;
	
	public BalanceAnual(Anual periodo) {
		super();
		this.periodo = periodo;
	}
	
	@Override
	public String getPeriodoNombre() {
		return periodo.toString();
	}

	@Override
	public Anual getPeriodo() {
		return periodo;
	}

	@Override
	public boolean sosAnual() {
		return true;
	}

	@Override
	public boolean sosSemestral() {
		return false;
	}
}
