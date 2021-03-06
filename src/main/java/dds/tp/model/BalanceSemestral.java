package dds.tp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.periodos.Semestral;

@Entity
@Observable
public class BalanceSemestral extends Balance {
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Semestral periodo;
	
	public BalanceSemestral() {
		// TODO Auto-generated constructor stub
	}
	
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
