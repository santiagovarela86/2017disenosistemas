package dds.tp.model.periodos;

public interface Periodo {
	
	public boolean igualA(String periodo);
	public boolean igualA(Semestral semestre);
	public boolean igualA(Anual semestre);
	public boolean igualA(Periodo semestre);

}
