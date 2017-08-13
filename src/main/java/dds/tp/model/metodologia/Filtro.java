package dds.tp.model.metodologia;

import java.util.List;
import java.util.stream.Collectors;

import dds.tp.model.Empresa;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Filtro {

	public List<ResultadoAnalisis> getResultadosNegativos(List<Empresa> empresas, CondicionTaxativa condicion,
			RepositorioIndicadores repoIndicadores) {

		return empresas.stream()
				.filter(e -> !condicion.evaluar(e, repoIndicadores))
				.map(e -> new ResultadoAnalisis(0, e, "No cumple la condicion " + condicion.getNombre()))
				.collect(Collectors.toList());
	}

	public List<Empresa> getEmpresasQueCumplen(List<Empresa> empresas, CondicionTaxativa condicion,
			RepositorioIndicadores repoIndicadores) {

		return empresas.stream()
				.filter(e -> condicion.evaluar(e, repoIndicadores))
				.collect(Collectors.toList());
	}
}
