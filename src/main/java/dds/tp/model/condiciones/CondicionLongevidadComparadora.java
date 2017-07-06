package dds.tp.model.condiciones;

import dds.tp.model.CondicionPriorizar;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionLongevidadComparadora extends CondicionPriorizar {
	
	public CondicionLongevidadComparadora(String nombre, String descripcion) {
		super(nombre,descripcion);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		return empresa1.getAntiguedad() < empresa2.getAntiguedad();		
	}

}
