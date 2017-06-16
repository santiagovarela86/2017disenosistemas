package dds.tp.parsertools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import dds.tp.excepciones.SintaxisIncorrecta;
import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;


public class Parser {

	public ArrayList<MyToken> parsear(String expresion) {
		try {
			InputStream is = new ByteArrayInputStream(expresion.getBytes( Charset.defaultCharset() ) );
			GramaticaParser parser = new GramaticaParser(is);
			return parser.parsear();
		}catch (ParseException e) {
			throw new SintaxisIncorrecta("Error en la expresion");
		}
	}
	
}
