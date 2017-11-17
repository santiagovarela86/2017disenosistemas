package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.DBManager;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.metodologia.Metodologia;

public class RepositorioUsuarios {
	
	private List<Usuario> usuarios = new ArrayList<>();

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void addUsuario(Usuario usuario) throws ElementoYaExiste {
		if(!this.contieneUsuario(usuario))
			this.usuarios.add(usuario);
		else
			throw new ElementoYaExiste("El usuario ya existe");
	}

	public Usuario getUsuario(String nombre) throws ElementoNotFound {
		if(this.contieneUsuario(nombre)) {
			return this.usuarios.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);	
		}
		else {
			throw new ElementoNotFound("No existe el usuario " + nombre);
		}
	}

	public boolean contieneUsuario(Usuario usuario) {
		return this.contieneUsuario(usuario.getNombre());
	}

	public boolean contieneUsuario(String nombre) {
		return this.usuarios.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}
	
	public void guardarUsuario(Usuario usuario) {
		DBManager.guardar(usuario);
	}
	
	public void actualizarUsuario(Usuario usuario) {
		DBManager.actualizar(usuario);
	}
	
	public void guardarUsuarios(List<Usuario> usuarios) {
		usuarios.stream().forEach(usuario->{if(usuario.estasGuardado())
												DBManager.actualizar(usuario);
											else
												DBManager.guardar(usuario);
											});
	}
	
	public List<Usuario> cargarUsuarios() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Usuario> usuarios = manager.createQuery("from Usuario", Usuario.class).getResultList();
		manager.close();
		return usuarios;
	}

	public List<Usuario> cargarUsuarios(String nombre) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Usuario> usuarios = manager.createQuery("SELECT t FROM Usuario t WHERE t.nombre = :nombre", Usuario.class)
				.setParameter("nombre", nombre).getResultList();
		manager.close();
		return usuarios;
	}
	
	public RepositorioUsuarios inicializar() {
		this.usuarios = this.cargarUsuarios();
		return this;
	}
	
	public void removerUsuario(String nombre) {
		this.usuarios = this.usuarios.stream().filter(elem->!elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList());
	}
	
	public void inicializarIndicadoresYMetodologias(Usuario usuario) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		PersistenceUnitUtil checkeadorDeLoad = manager.getEntityManagerFactory().getPersistenceUnitUtil();
		if(checkeadorDeLoad.isLoaded(usuario, "indicadores") && checkeadorDeLoad.isLoaded(usuario, "metodologias"))
		{
			manager.close();
			return;
		}
		@SuppressWarnings("unchecked")
		List<Indicador> indicadores = manager.createQuery("SELECT b FROM Indicador b WHERE usuario_id = :usuario_id")
				.setParameter("usuario_id", usuario.getId()).getResultList();
		
		@SuppressWarnings("unchecked")
		List<Metodologia> metodologias = manager.createQuery("SELECT b FROM Metodologia b WHERE usuario_id = :usuario_id")
				.setParameter("usuario_id", usuario.getId()).getResultList();
		
		manager.close();
		usuario.setIndicadores(indicadores);
		usuario.setMetodologias(metodologias);
	}
	
}
