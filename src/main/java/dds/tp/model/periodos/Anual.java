package dds.tp.model.periodos;

import java.time.Year;

public class Anual implements Periodo {
	
	private int anio;
	
	public Anual(int anio) {
		this.anio = anio;
	}

	public Anual(String anio) {
		this.anio = Integer.parseInt(anio);
	}
	
	public int getAnio() {
		return anio;
	}
	
	public Anual anioSiguiente() {
		return new Anual(this.anio+1);
	}
	
	public Anual anioAnterior(){
		return new Anual(this.anio-1);
	}
	
	public boolean igualA(Anual anio){
		return this.anio == anio.getAnio();
	}
	
	public static Anual getPeriodoActual() {
		return new Anual(Year.now().getValue());
	}
	
	@Override
	public String toString() {
		return anio+"";
	}

	@Override
	public boolean igualA(String periodo) {
		try {
			return new Anual(periodo).igualA(this);
		}catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public boolean igualA(Semestral semestre) {
		return false;
	}

	@Override
	public boolean igualA(Periodo periodo) {
		if(periodo.getClass() == Anual.class) {
			return this.igualA((Anual)periodo);
		}
		if(periodo.getClass() == Semestral.class){
			return this.igualA((Semestral)periodo);
		}
		return false;
	}

}
