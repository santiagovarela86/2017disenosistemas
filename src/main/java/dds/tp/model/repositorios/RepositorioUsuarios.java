package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Balance;
import dds.tp.model.BalanceAnual;
import dds.tp.model.BalanceSemestral;
import dds.tp.model.Empresa;
import dds.tp.model.Usuario;

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
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(usuario);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public void actualizarUsuario(Usuario usuario) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.merge(usuario);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public void guardarUsuarios(List<Usuario> usuarios) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			usuarios.stream().forEach(usuario->{if(usuario.estasGuardado())
													manager.merge(usuario);
												else
													manager.persist(usuario);
												});
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
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
	
	public void cargarUsuariosCargados() {
		this.usuarios = this.cargarUsuarios();
	}
	
	public Usuario cargarUsuarioDefault() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		Usuario usuario = manager.createQuery(
				"SELECT u FROM Usuario u WHERE u.nombre LIKE 'default'", Usuario.class)
			    .getResultList()
			    .get(0);
		manager.close();
		return usuario;
	}
	
	public RepositorioUsuarios obtenerRepoCompleto(){
		this.usuarios = this.cargarUsuarios();
		return this;
	}
	
}

