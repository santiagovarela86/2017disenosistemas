package dds.tp.model.periodos;

public class Anio implements Periodo {
	
	private int anio;
	
	public Anio(int anio) {
		this.anio = anio;
	}

	public Anio(String anio) {
		this.anio = Integer.parseInt(anio);
	}
	
	public int getAnio() {
		return anio;
	}
	
	public Anio anioSiguiente() {
		return new Anio(this.anio+1);
	}
	
	public Anio anioAnterior(){
		return new Anio(this.anio-1);
	}
	
	public boolean igualA(Anio anio){
		return this.anio == anio.getAnio();
	}
	
	@Override
	public String toString() {
		return anio+"";
	}

	@Override
	public boolean igualA(String periodo) {
		return new Anio(periodo).igualA(this);
	}

}
