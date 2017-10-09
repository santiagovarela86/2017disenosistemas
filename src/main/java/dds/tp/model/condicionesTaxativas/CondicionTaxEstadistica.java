package dds.tp.model.condicionesTaxativas;

import javax.persistence.Convert;

import dds.tp.jpa.converters.ModoEstadisticoConverter;
import dds.tp.model.Empresa;
import dds.tp.model.condiciones.Comparado;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionTaxEstadistica extends CondicionTaxativa {

	@Convert(converter=ModoEstadisticoConverter.class)
	private ModoEstadistico modo;
	
	public CondicionTaxEstadistica() {
		// TODO Auto-generated constructor stub
	}
	
	public CondicionTaxEstadistica(String nombre, String descripcion, Comparado indicador, Comparador comparador,
			int periodosHaciaAtras, Double valorLimite,ModoEstadistico modo) {		
		super(nombre, descripcion, indicador, comparador, periodosHaciaAtras,valorLimite);
		this.modo = modo;
	}
	
	@Override
	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		return this.comparador.comparar(modo.getEstadistica(empresa, this.indicador, repoIndicadores),this.valorLimite);
	}
}
