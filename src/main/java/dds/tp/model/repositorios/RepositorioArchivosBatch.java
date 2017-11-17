package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.batch.ArchivoBatch;
import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.DBManager;

public class RepositorioArchivosBatch {
	
	private List<ArchivoBatch> archivos = new ArrayList<>();

	public List<ArchivoBatch> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<ArchivoBatch> archivos) {
		this.archivos = archivos;
	}

	public void addArchivo(ArchivoBatch archivo) throws ElementoYaExiste {
		if(!this.contieneArchivo(archivo))
			this.archivos.add(archivo);
		else
			throw new ElementoYaExiste("El archivo ya existe");
	}

	public ArchivoBatch getArchivo(String nombre) throws ElementoNotFound {
		if(this.contieneArchivo(nombre)) {
			return this.archivos.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);	
		}
		else {
			throw new ElementoNotFound("No existe el archivo " + nombre);
		}
	}

	public boolean contieneArchivo(ArchivoBatch archivo) {
		return this.contieneArchivo(archivo.getNombre());
	}

	public boolean contieneArchivo(String nombre) {
		return this.archivos.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}
	
	public void guardarArchivo(ArchivoBatch archivo) {
		DBManager.guardar(archivo);
	}
	
	public void actualizarArchivo(ArchivoBatch archivo) {
		DBManager.actualizar(archivo);
	}
	
	public void guardarArchivos(List<ArchivoBatch> archivos) {
		archivos.stream().forEach(archivo->{if(archivo.estasGuardado())
												DBManager.actualizar(archivo);
											else
												DBManager.guardar(archivo);
											});
	}
	
	public List<ArchivoBatch> cargarArchivos() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<ArchivoBatch> archivos = manager.createQuery("from ArchivoBatch", ArchivoBatch.class).getResultList();
		manager.close();
		return archivos;
	}

	public List<ArchivoBatch> cargarArchivos(String nombre) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<ArchivoBatch> archivos = manager.createQuery("SELECT t FROM ArchivoBatch t WHERE t.nombre = :nombre", ArchivoBatch.class)
				.setParameter("nombre", nombre).getResultList();
		manager.close();
		return archivos;
	}
	
	public RepositorioArchivosBatch inicializar() {
		this.archivos = this.cargarArchivos();
		return this;
	}
		
}

