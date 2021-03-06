package dds.tp.model.condiciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@DiscriminatorValue("condPriorizante")
public class CondicionPriorizante extends Condicion {

	public CondicionPriorizante() {
		// TODO Auto-generated constructor stub
	}
	
	public CondicionPriorizante(String nombre, String descripcion, Comparable indicador, Comparador comparador,
			int periodosAEvaluar) {
		super(nombre, descripcion, indicador, comparador, periodosAEvaluar);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		return this.getPeriodosAEvaluar().stream().allMatch(periodo -> emp1EsMejorQueEmp2(empresa1, empresa2, repoIndicadores, (Anual) periodo));
	}
	
	private boolean emp1EsMejorQueEmp2(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores, Anual anio) {
		return this.comparador
				.comparar(obtenerResultado(empresa1, anio, repoIndicadores), obtenerResultado(empresa2, anio, repoIndicadores));
	}
	
	private Double obtenerResultado(Empresa empresa, Anual anio, RepositorioIndicadores repoIndicadores){
		return this.getIndicador().evaluar(empresa, empresa.getBalance(anio), repoIndicadores);
	}
	
}
