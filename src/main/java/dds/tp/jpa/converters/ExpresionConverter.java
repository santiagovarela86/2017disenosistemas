package dds.tp.jpa.converters;

import javax.persistence.AttributeConverter;

import dds.tp.calculador.Expresion;
import dds.tp.parsertools.Parser;

public class ExpresionConverter implements AttributeConverter<Expresion, String> {

	@Override
	public String convertToDatabaseColumn(Expresion expresion) {
		return expresion.toString();
	}

	@Override
	public Expresion convertToEntityAttribute(String exp) {
		return new Expresion(new Parser().parsear(exp));
	}

}
