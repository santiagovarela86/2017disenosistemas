package dds.tp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.periodos.Semestral;

@Entity
@DiscriminatorValue("balanceSemestral")
@Observable
public class BalanceSemestral extends Balance {
	
	@OneToOne
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
