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
	
	public boolean contieneIndicador(String nombre) {
		return this.indicadores.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}

	public Indicador getIndicador(String nombre) throws ElementoNotFound {
		if(!this.contieneIndicador(nombre)){
			throw new ElementoNotFound("No existe el indicador " + nombre);
		}
		return this.indicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
	}
	@Deprecated
	public void cargarPredeterminados() {
		RepositorioUsuarios repoUsuarios = new RepositorioUsuarios();
		repoUsuarios.cargarUsuariosCargados();
		Usuario usuarioDefault = repoUsuarios.getUsuario("default");		
		
		this.indicadores.add(new Indicador("Ingreso Neto", "ingresoNetoEnOperacionesContinuas+ingresoNetoEnOperacionesContinuas", usuarioDefault));
		this.indicadores.add(new Indicador("Razon Corriente", "activoCorriente/pasivoCorriente", usuarioDefault));
		this.indicadores.add(new Indicador("ROA", "utilidadBruta/activoTotal", usuarioDefault));
		//Pongo indicador antes xq sino tiene la cuenta se llama recursivamente el mismo indicador y tira stackoverflow
		//Normalmente un indicador no se llama igual q el calculo...
		this.indicadores.add(new Indicador("Indicador ROE", "roe", usuarioDefault));
		this.indicadores.add(new Indicador("Indicador Endeudamiento", "endeudamiento", usuarioDefault));
		this.indicadores.add(new Indicador("Indicador Margen", "margen", usuarioDefault));
		
		for (Indicador i : this.getIndicadores()){
			usuarioDefault.addIndicador(i);
		}
		repoUsuarios.actualizarUsuario(usuarioDefault);
		
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

	public void cargarIndicadoresGuardados() {
		this.indicadores = this.cargarIndicadores();
	}
	
	public void cargarIndicadoresPublicosGuardados() {
		this.indicadores = this.cargarIndicadoresPublicos();
	}
	
}
