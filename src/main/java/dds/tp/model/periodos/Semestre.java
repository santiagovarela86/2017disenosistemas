package dds.tp.model.periodos;

public class Semestre implements Periodo {
	
	private int numeroDeSemestre;
	private int anio;
	
	public Semestre(String periodo) {
		periodo = periodo.trim();
		this.anio = Integer.parseInt(periodo.substring(periodo.length()-4, periodo.length()));
		this.numeroDeSemestre = Integer.parseInt(periodo.substring(0, 1));
	}
	
	public Semestre(int numero, int anio) {
		this.numeroDeSemestre = numero;
		this.anio = anio;
	}
	
	public int getAnio() {
		return anio;
	}
	
	public int getNumeroDeSemestre() {
		return numeroDeSemestre;
	}
	
	public Semestre semestreSiguiente(){
		if(this.numeroDeSemestre==2){
			return new Semestre(1, this.anio+1);
		}else {
			return new Semestre(2, this.anio);
		}
	}
	
	public Semestre semestreAnterior(){
		if(this.numeroDeSemestre==1){
			return new Semestre(2, this.anio-1);
		}else {
			return new Semestre(1, this.anio);
		}
	}
	
	public boolean igualA(Semestre sem) {
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
		return this.igualA(new Semestre(periodo));
	}
}
