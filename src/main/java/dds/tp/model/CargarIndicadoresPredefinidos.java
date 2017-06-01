package dds.tp.model;

import dds.tp.excepciones.ElementoYaExiste;

public class CargarIndicadoresPredefinidos {
	Indicador indicadorIngresoNeto = new Indicador("ingresoNeto"
							,"cuenta(ingresoNetoEnOperacionesContinuas)+cuenta(ingresoNetoEnOperacionesContinuas)");
	Indicador indicadorRazonCorriente = new Indicador("razonCorriente","cuenta(activoCorriente)/cuenta(pasivoCorriente)");
	Indicador indicadorROA = new Indicador("ROA","cuenta(utilidadBruta)/cuenta(activoTotal)");
	Indicador indicadorEndeudamiento = new Indicador("endeudamiento","cuenta(pasivoTotalTerceros)/cuenta(activoTotal)");
	
	public void cargar(GuardadorIndicadores guardadorIndicadores) throws ElementoYaExiste{ 

		guardadorIndicadores.addIndicador(indicadorIngresoNeto);
		guardadorIndicadores.addIndicador(indicadorRazonCorriente);
		guardadorIndicadores.addIndicador(indicadorROA);
		guardadorIndicadores.addIndicador(indicadorEndeudamiento);
		
	}
	
}
