package dds.tp.model;

import dds.tp.excepciones.ElementoYaExiste;

public class CargarIndicadoresPredefinidos {
	Indicador IngresoNeto = new Indicador("IngresoNeto","ingresoNetoEnOperacionesContinuas + ingresoNetoEnOperacionesContinuas");
	Indicador RazonCorriente = new Indicador("RazonCorriente","activoCorriente / pasivoCorriente");
	Indicador ROA = new Indicador("ROA","utilidadBruta / activoTotal");
	Indicador Endeudamiento = new Indicador("Endeudamiento","pasivoTotalTerceros / activoTotal");
	
	public void cargar(GuardadorIndicadores guardadorIndicadores) throws ElementoYaExiste{ 

		guardadorIndicadores.addIndicador(IngresoNeto);
		guardadorIndicadores.addIndicador(RazonCorriente);
		guardadorIndicadores.addIndicador(ROA);
		guardadorIndicadores.addIndicador(Endeudamiento);
		
	}
	
}
