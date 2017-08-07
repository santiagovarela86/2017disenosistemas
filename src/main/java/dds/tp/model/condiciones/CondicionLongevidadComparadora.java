package dds.tp.model.condiciones;

import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionLongevidadComparadora extends Condicion {
	
	public CondicionLongevidadComparadora(String nombre, String descripcion) {
		super(nombre,descripcion);
	}

	public boolean evaluar(Empresa empresa1, Empresa empresa2, RepositorioIndicadores repoIndicadores){
		return empresa1.getAntiguedad() > empresa2.getAntiguedad();		
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		//Pass
	}

}
