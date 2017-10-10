package dds.tp.model.condicionesTaxativas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparable;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.periodos.Anual;
import dds.tp.model.repositorios.RepositorioIndicadores;

@Entity
@DiscriminatorValue("condTaxativaPendiente")
public class CondicionTaxPendiente extends CondicionTaxativa {

	public CondicionTaxPendiente() {
		// TODO Auto-generated constructor stub
	}
	
	public CondicionTaxPendiente(String nombre, String descripcion, Comparable indicador, Comparador comparador,
			int periodosHaciaAtras, Double valorLimite) {		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras,valorLimite);
	}
	
	@Override
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return this.getPeriodosAEvaluar().stream()
				.allMatch(periodo -> this.comparador.comparar(this.indicador.evaluar(empresa, empresa.getBalance(periodo), repoIndicadores),
						this.indicador.evaluar(empresa, empresa.getBalance(((Anual)periodo).anioAnterior()), repoIndicadores)));
	}
}
