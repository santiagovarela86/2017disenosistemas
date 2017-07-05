package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;
import dds.tp.model.repositorios.RepositorioIndicadores;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionEstadistica extends Condicion {

	private Indicador indicador;
	private ModoEstadistico modoEstadistico; //promedio, mediana o sumatoria
	private Comparador comparador;
	private Double valorAComparar;
	
	public CondicionEstadistica(String nombre, String descripcion, Indicador indicador, 
									Comparador comparador, ModoEstadistico modo, Double valorAComparar) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.modoEstadistico = modo;
		this.valorAComparar = valorAComparar;
	}

	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores){
		Double resultadoEstadistico = modoEstadistico.getEstadistica(empresa, indicador, repoIndicadores);
		return comparador.comparar(resultadoEstadistico, valorAComparar);
	}
}
