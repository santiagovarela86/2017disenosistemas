package dds.tp.Spark.util;

import lombok.*;

public class Path {

	public static class Web {
		@Getter public static final String LOGIN = "/";
		@Getter public static final String PANTALLA_PRINCIPAL = "/login/";
		@Getter public static final String LOGOUT = "/logout/";
		
		@Getter public static final String VISUALIZAR_CUENTAS = "/visualizarCuentas/";
		
		@Getter public static final String CREAR_INDICADOR = "/crearIndicador/";
		
		@Getter public static final String EVALUAR_INDICADOR = "/evaluarIndicador/";
		@Getter public static final String EVALUAR_INDICADOR_INGRESADO = "/evaluarIndicadorIngresado/";
		
		@Getter public static final String EVALUAR_METODOLOGIA = "/evaluarMetodologia/";
		@Getter public static final String EVALUAR_METODOLOGIA_INGRESADA = "/evaluarMetodologiaIngresado/";

	}

	public static class Template {

		public final static String LOGIN = "/templates/mostrarLogin.vm";
		public final static String PANTALLA_PRINCIPAL = "/templates/pantallaPrincipal.vm";

		public final static String VISUALIZAR_CUENTAS="/templates/visualizarCuentas.vm";
		
		public final static String CREAR_INDICADOR="/templates/crearIndicador.vm";
		
		public final static String EVALUAR_INDICADOR="/templates/evaluarIndicador.vm";
		
		public final static String EVALUAR_METODOLOGIA="/templates/evaluarMetodologia.vm";
		public final static String EVALUAR_METODOLOGIA_INGRESADA="/templates/evaluarMetodologiaResultados.vm";
	
		public static final String NOT_FOUND = "/templates/notFound.vm";
		
		
		
	}

}
