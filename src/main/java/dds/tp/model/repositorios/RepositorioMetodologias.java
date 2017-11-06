package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnitUtil;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.Usuario;
import dds.tp.model.builders.MetodologiaBuilder;
import dds.tp.model.condiciones.CondicionPriorizante;
import dds.tp.model.condiciones.CondicionTaxativa;
import dds.tp.model.condiciones.EvaluadorAntiguedad;
import dds.tp.model.condiciones.comparadores.Mayor;
import dds.tp.model.condiciones.comparadores.Menor;
import dds.tp.model.condicionesTaxativas.CondicionTaxPendiente;
import dds.tp.model.condicionesTaxativas.CondicionTaxativaSimple;
import dds.tp.model.metodologia.Metodologia;

public class RepositorioMetodologias {
	
	private List<Metodologia> metodologias = new ArrayList<>();
	
	public List<Metodologia> getMetodologias() {
		return metodologias;
	}
	
	public void setMetodologias(List<Metodologia> metodologias2) {
		this.metodologias =  metodologias2;
	}

	public boolean contieneMetodologia(String nombre) {
		return this.metodologias.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}

	public void addMetodologia(Metodologia metodologia) throws ElementoYaExiste {
		if(this.contieneMetodologia(metodologia.getNombre())) {
			throw new ElementoYaExiste("Ya existe una metodologia con este nombre");
		}
		this.guardarMetodologia(metodologia);
		this.metodologias.add(metodologia);
	}
	@Deprecated
	public void cargarPredeterminados(RepositorioIndicadores repoIndicadores, Usuario user) {		
		Metodologia warrenBuffet = new MetodologiaBuilder().setNombre("Warren Buffet")
			.agregarCondPriorizar(new CondicionPriorizante("Maximizar ROE", "Maximizar ROE", repoIndicadores.getIndicador("Indicador ROE"), new Mayor(), 7))
			.agregarCondPriorizar(new CondicionPriorizante("Minimizar DEUDA","Minimizar DEUDA", repoIndicadores.getIndicador("Indicador ENDEUDAMIENTO"), new Menor(), 1))
			.agregarCondTaxativa(new CondicionTaxPendiente("Margenes Crecientes", "Margenes Crecientes", repoIndicadores.getIndicador("Indicador MARGEN"), new Menor(), 8, null))
			.agregarCondTaxativa(new CondicionTaxativaSimple("Longevidad Simple", "Longevidad Simple", new EvaluadorAntiguedad(), new Mayor(), 1, 10d))
			.agregarCondPriorizar(new CondicionPriorizante("Mas Antigua", "Mas Antigua", new EvaluadorAntiguedad(), new Mayor(), 1))			
			.build();
		warrenBuffet.setUsuario(user);
		this.metodologias.add(warrenBuffet);
	}
	
	public Metodologia getMetodologia(String nombre) {
		if(!this.contieneMetodologia(nombre)){
			throw new ElementoNotFound("No existe la metodología " + nombre);
		}
		return this.metodologias.stream()
				.filter(metodologia -> metodologia.getNombre().equalsIgnoreCase(nombre))
				.findFirst().get();
	}
	
	public List<Metodologia> cargarMetodologias() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Metodologia> metodologias = manager.createQuery("from Metodologia", Metodologia.class).getResultList();
		manager.close();
		return metodologias;
	}
	
	public void guardarMetodologia(Metodologia metodologia) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Metodologia metodologiaGuardada = manager.merge(metodologia);
			metodologia.setId(metodologiaGuardada.getId());
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		}finally {
			manager.close();
		}
	}
	
	public static void inicializarCondiciones(Metodologia metodologia) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		PersistenceUnitUtil checkeadorDeLoad = manager.getEntityManagerFactory().getPersistenceUnitUtil();
		
		if(checkeadorDeLoad.isLoaded(metodologia, "condicionesTaxativas") && checkeadorDeLoad.isLoaded(metodologia, "condicionesQuePriorizan")) 
		{
			manager.close();
			return;
		}
		@SuppressWarnings("unchecked")
		List<CondicionTaxativa> condicionesTaxativas = manager.createQuery("SELECT c FROM Condicion c WHERE tipoCondicion != :tcondicion AND metodologia_id = :meto_id")
				.setParameter("tcondicion", "condPriorizante").setParameter("meto_id", metodologia.getId()).getResultList();
		@SuppressWarnings("unchecked")
		List<CondicionPriorizante> condicionesPriorizantes = manager.createQuery("SELECT c FROM Condicion c WHERE tipoCondicion = :tcondicion AND metodologia_id = :meto_id")
				.setParameter("tcondicion", "condPriorizante").setParameter("meto_id", metodologia.getId()).getResultList();
		manager.close();
		metodologia.setCondicionesQuePriorizan(condicionesPriorizantes);
		metodologia.setCondicionesTaxativas(condicionesTaxativas);
	}

	public void cargarMetodologiaGuardadas() {
		this.metodologias = (ArrayList<Metodologia>) this.cargarMetodologias();	
	}
	
}
