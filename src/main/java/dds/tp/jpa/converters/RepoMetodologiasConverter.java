package dds.tp.jpa.converters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;

import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.repositorios.RepositorioMetodologias;

public class RepoMetodologiasConverter implements AttributeConverter<RepositorioMetodologias, List<Metodologia>> {

	@Override
	public List<Metodologia> convertToDatabaseColumn(RepositorioMetodologias repo) {
		return repo.getMetodologias();
	}

	@Override
	public RepositorioMetodologias convertToEntityAttribute(List<Metodologia> metos) {
		// TODO Auto-generated method stub
		RepositorioMetodologias repoMeto =  new RepositorioMetodologias();
		repoMeto.setMetodologias((ArrayList<Metodologia>) metos);
		return repoMeto;
	}

}
