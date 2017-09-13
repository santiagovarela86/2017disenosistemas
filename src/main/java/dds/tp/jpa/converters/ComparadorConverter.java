package dds.tp.jpa.converters;

import javax.persistence.AttributeConverter;

import dds.tp.model.condiciones.comparadores.Comparador;

public class ComparadorConverter implements AttributeConverter<Comparador, String> {

	@Override
	public String convertToDatabaseColumn(Comparador comp) {
		return comp.getNombre();
	}

	@Override
	public Comparador convertToEntityAttribute(String arg0) {
		return Comparador.crearComparador(arg0);
	}

}
