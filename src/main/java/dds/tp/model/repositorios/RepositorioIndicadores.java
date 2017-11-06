package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;

public class RepositorioIndicadores {

	private List<Indicador> indicadores;
	
	public RepositorioIndicadores() {
		super();
		this.indicadores = new ArrayList<>();
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
	public void addIndicador(Indicador indc) throws ElementoYaExiste {
		if(this.contieneIndicador(indc.getNombre())) {
			throw new ElementoYaExiste("Ya existe un indicador con este nombre");
		}
		this.indicadores.add(indc);
		this.guardarIndicador(indc);
	}
	
	public boolean contieneIndicador(String nombreIndicador) {
		return this.indicadores.stream().anyMatch(
				elem -> elem.getNombre().equalsIgnoreCase(nombreIndicador));
	}

	public Indicador getIndicador(String nombreIndicador) throws ElementoNotFound {
		if(!this.contieneIndicador(nombreIndicador)){
			throw new ElementoNotFound("No existe el indicador " + nombreIndicador);
		}
		return this.indicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombreIndicador)).collect(Collectors.toList()).get(0);
	}
	@Deprecated
	public void cargarPredeterminados(Usuario userPredeterminado) {		
		this.indicadores.add(new Indicador("Ingreso Neto", "ingresoNetoEnOperacionesContinuas+ingresoNetoEnOperacionesContinuas", userPredeterminado));
		this.indicadores.add(new Indicador("Razon Corriente", "activoCorriente/pasivoCorriente", userPredeterminado));
		this.indicadores.add(new Indicador("ROA", "utilidadBruta/activoTotal", userPredeterminado));
		//Pongo indicador antes xq sino tiene la cuenta se llama recursivamente el mismo indicador y tira stackoverflow
		//Normalmente un indicador no se llama igual q el calculo...
		this.indicadores.add(new Indicador("Indicador ROE", "roe", userPredeterminado));
		this.indicadores.add(new Indicador("Indicador Endeudamiento", "endeudamiento", userPredeterminado));
		this.indicadores.add(new Indicador("Indicador Margen", "margen", userPredeterminado));
		this.indicadores.forEach(ind->userPredeterminado.addIndicador(ind));
	}
	
	public void guardarIndicador(Indicador indicador) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(indicador);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public void guardarIndicadores(List<Indicador> indicadores) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			indicadores.stream().forEach(indicador->manager.persist(indicador));
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public List<Indicador> cargarIndicadores() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Indicador> indicadores = manager.createQuery("from Indicador", Indicador.class).getResultList();
		manager.close();
		return indicadores;
	}
	
	public List<Indicador> cargarIndicadoresPublicos() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Indicador> indicadores = manager.createQuery(
				"SELECT i FROM Indicador i, IN (i.usuario) AS u WHERE u.nombre = 'default'", Indicador.class)
					.getResultList();
		manager.close();
		return indicadores;
	}

	public List<Indicador> cargarIndicadoresPorUsuario(Usuario usuario) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Indicador> indicadores = manager.createQuery(
				"SELECT i FROM Indicador i, IN (i.usuario) AS u WHERE u.nombre = :custName", Indicador.class)
					.setParameter("custName", usuario.getNombre())
						.getResultList();
		manager.close();
		return indicadores;
	}
	
	public void cargarIndicadoresGuardados() {
		this.indicadores = this.cargarIndicadores();
	}
	
	public RepositorioIndicadores obtenerRepositorioCompleto() {
		this.indicadores = this.cargarIndicadores();
		return this;
	}
	
	public void cargarIndicadoresPublicosGuardados() {
		this.indicadores = this.cargarIndicadoresPublicos();
	}
	
	public RepositorioIndicadores obtenerIndicadoresPublicosGuardados() {
		this.indicadores = this.cargarIndicadoresPublicos();
		return this;
	}
	
	public void cargarIndicadoresDelUsuario(Usuario usuario) {
		this.indicadores = this.cargarIndicadoresPorUsuario(usuario);
	}
	
	public void addIndicadores(List<Indicador> indicadores) {
		for (Indicador i : indicadores){
			this.getIndicadores().add(i);
		}
	}
	
}
