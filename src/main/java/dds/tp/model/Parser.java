package dds.tp.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import dds.tp.calculador.Termino;
import dds.tp.evaluador.EvaluadorIndicador;
import dds.tp.excepciones.CuentaNotFound;
import dds.tp.excepciones.IndicadorNotFound;
import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;

public class Parser {

	public void chequearExpresion(String expresion) throws ParseException {
		InputStream is = new ByteArrayInputStream(expresion.getBytes( Charset.defaultCharset() ) );
		GramaticaParser parser = new GramaticaParser(is);
		parser.analizarSintacticamente();
	}
	
	public Termino obtenerTerminoCalculable(String expresion, EvaluadorIndicador ev) throws ParseException, CuentaNotFound, IndicadorNotFound {
		InputStream is = new ByteArrayInputStream(expresion.getBytes( Charset.defaultCharset() ) );
		GramaticaParser parser = new GramaticaParser(is);
		return parser.aevaluar(ev);
	}
	
	
}
