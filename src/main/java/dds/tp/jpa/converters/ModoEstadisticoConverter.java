package dds.tp.jpa.converters;

import javax.persistence.AttributeConverter;

import dds.tp.model.condiciones.modosestadisticos.ModoEstadistico;

public class ModoEstadisticoConverter implements AttributeConverter<ModoEstadistico, String> {

	@Override
	public String convertToDatabaseColumn(ModoEstadistico modoEstadistico) {
		return modoEstadistico.toString();
	}

	@Override
	public ModoEstadistico convertToEntityAttribute(String nombre) {
		return ModoEstadistico.crearModoEstatistico(nombre);
	}

}
