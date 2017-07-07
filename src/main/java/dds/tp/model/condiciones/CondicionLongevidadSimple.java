package dds.tp.model.condiciones;

import dds.tp.model.CondicionTaxativa;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class CondicionLongevidadSimple extends CondicionTaxativa {

	public CondicionLongevidadSimple(String nombre, String descripcion) {
		super(nombre, descripcion);
	}

	public boolean evaluar(Empresa empresa, RepositorioIndicadores repoIndicadores){
		return empresa.getAntiguedad() > 10;
	}

	@Override
	public void evaluarRequisitosEn(Empresa empresa, RepositorioIndicadores repoIndicadores) {
		// Pass	
	}
	
}
