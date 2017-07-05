package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;
import dds.tp.model.repositorios.RepositorioIndicadores;

//ESTA SE USA PARA ORDENAR
public class CondicionComparadora extends Condicion {

	private int periodosHaciaAtras;
	private Indicador indicador;
	private Comparador comparador;
	
	public CondicionComparadora(String nombre,String descripcion, Indicador indicador, Comparador comparador, int periodosHaciaAtras) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.periodosHaciaAtras = periodosHaciaAtras;
	}
	
	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		int anio = 2017;
		String semestre1 = "1ER SEMESTRE ";
		String semestre2 = "2DO SEMESTRE ";
		String periodo;
		boolean continuar = true;
		for (int i = 0; i < periodosHaciaAtras && continuar; i++) {
			if(i/2==0){
				periodo = semestre1 + anio;
				if(i>0){
					anio--;
				}
			} else {
				periodo = semestre2 + anio;
			}
			Double resultado1 = indicador.evaluar(empresa1.getBalance(periodo), repoIndicadores);
			Double resultado2 = indicador.evaluar(empresa2.getBalance(periodo), repoIndicadores);
			continuar = comparador.comparar(resultado1, resultado2);
		}
		return continuar;
	}
}
