package dds.tp.model.periodos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Periodo {

	@Id
	@GeneratedValue
	private Long id;
	
	public abstract boolean igualA(String periodo);
	public abstract boolean igualA(Semestral semestre);
	public abstract boolean igualA(Anual semestre);
	public abstract boolean igualA(Periodo semestre);

}
