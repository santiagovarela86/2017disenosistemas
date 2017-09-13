package dds.tp.model.periodos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("periodoSemestral")
public class Semestral extends Periodo {

	private int numeroDeSemestre;
	private int anio;
	
	public Semestral(String periodo) {
		periodo = periodo.trim();
		this.anio = Integer.parseInt(periodo.substring(periodo.length()-4, periodo.length()));
		this.numeroDeSemestre = Integer.parseInt(periodo.substring(0, 1));
	}
	
	public Semestral(int numero, int anio) {
		this.numeroDeSemestre = numero;
		this.anio = anio;
	}
	
	public Semestral() {
		// TODO Auto-generated constructor stub
	}
	
	public int getAnio() {
		return anio;
	}
	
	public int getNumeroDeSemestre() {
		return numeroDeSemestre;
	}
	
	public Semestral semestreSiguiente(){
		if(this.numeroDeSemestre==2){
			return new Semestral(1, this.anio+1);
		}else {
			return new Semestral(2, this.anio);
		}
	}
	
	public Semestral semestreAnterior(){
		if(this.numeroDeSemestre==1){
			return new Semestral(2, this.anio-1);
		}else {
			return new Semestral(1, this.anio);
		}
	}
	
	public boolean igualA(Semestral sem) {
		return sem.getAnio() == this.anio && sem.getNumeroDeSemestre() == this.numeroDeSemestre;
	}
	
	@Override
	public String toString() {
		if(this.numeroDeSemestre == 1) {
			return "1ER SEMESTRE " + anio;
		}else {
			return "2DO SEMESTRE " + anio;
		}
	}

	@Override
	public boolean igualA(String periodo) {
		try {
			return this.igualA(new Semestral(periodo));
		}catch(NumberFormatException e){
			return false;
		}
	}

	@Override
	public boolean igualA(Anual semestre) {
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
