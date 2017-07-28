package dds.tp.model.metodologia;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Condicion;
import dds.tp.model.CondicionTaxativa;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Filtro {

	public ArrayList<ResultadoAnalisis> getResultadosNegativos(List<Empresa> empresas, Condicion condicion, RepositorioIndicadores repoIndicadores){
		ArrayList<ResultadoAnalisis> empresasQueNoCumplen = new ArrayList<>();
		for (Empresa empresa : empresas) {
			if(!condicion.evaluar(empresa, repoIndicadores)){
				empresasQueNoCumplen.add(new ResultadoAnalisis(0, empresa, "No cumple la condicion " + condicion.getNombre()));
			}
		}
		return empresasQueNoCumplen;
	}
	
	public ArrayList<Empresa> getEmpresasQueCumplen(List<Empresa> empresas, CondicionTaxativa condicion, RepositorioIndicadores repoIndicadores){
		ArrayList<Empresa> empresasQueCumplen = new ArrayList<>();
		for (Empresa empresa : empresas) {
				if(condicion.evaluar(empresa, repoIndicadores)){
					empresasQueCumplen.add(empresa);
				}
		}
		return empresasQueCumplen;
	}
}
