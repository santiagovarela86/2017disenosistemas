package dds.tp.jpa.converters;

import java.util.List;

import javax.persistence.AttributeConverter;

import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class RepoIndicadoresConverter implements AttributeConverter<RepositorioIndicadores, List<Indicador>>{

	@Override
	public List<Indicador> convertToDatabaseColumn(RepositorioIndicadores arg0) {
		return arg0.getIndicadores();
	}

	@Override
	public RepositorioIndicadores convertToEntityAttribute(List<Indicador> arg0) {
		RepositorioIndicadores repoIndicadores =  new RepositorioIndicadores();
		repoIndicadores.addIndicadores(arg0);
		return repoIndicadores;
	}

}
