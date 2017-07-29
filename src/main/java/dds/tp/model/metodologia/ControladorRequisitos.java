package dds.tp.model.metodologia;

import java.util.ArrayList;
import java.util.List;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class ControladorRequisitos {

	public List<ResultadoAnalisis> getEmpresasQueNoCumplenLosRequisitos(List<Empresa> empresas,
			List<Condicion> condiciones, RepositorioIndicadores repoIndicadores) {
		ArrayList<ResultadoAnalisis> resultadosAnalisis = new ArrayList<>();
		for (Empresa empresa : empresas) {
			try {
				for (Condicion condicion : condiciones) {
					condicion.evaluarRequisitosEn(empresa, repoIndicadores);
				}
			} catch (Exception ex) {
				resultadosAnalisis.add(
						new ResultadoAnalisis(0, empresa, "No hay suficiente informacion para evaluar la empresa"));
			}
		}
		return resultadosAnalisis;
	}
}
