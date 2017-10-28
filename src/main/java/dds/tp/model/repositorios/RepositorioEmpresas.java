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

public class RepositorioEmpresas {
	
	private List<Empresa> empresas;
	
	public RepositorioEmpresas() {
		super();
		this.empresas = new ArrayList<>();
	}
	
	public RepositorioEmpresas(List<Empresa> empresas){
		this.empresas = empresas;
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public void addEmpresa(Empresa emp) throws ElementoYaExiste {
		if(!this.contieneEmpresa(emp))
			this.empresas.add(emp);
		else
			throw new ElementoYaExiste("Empresa ya existe, pruebe unir empresa");
	}
	
	public void agregarEmpresaYaExistente(Empresa empresaConDatosAUnir) {
		Empresa emprExistente = this.getEmpresa(empresaConDatosAUnir.getNombre());
		for (Balance balanceAUnir : empresaConDatosAUnir.getTodosLosBalances()) {
			if(!emprExistente.contieneBalance(balanceAUnir))
				emprExistente.addBalance(balanceAUnir);
			else
				emprExistente.unirBalanceConUnoYaExistente(balanceAUnir);
		}
	}
	
	public void addEmpresas(List<Empresa> empresasNuevas) {
			empresasNuevas.stream().forEach(empresa->{if(!this.contieneEmpresa(empresa))
															this.addEmpresa(empresa);
														else
															this.agregarEmpresaYaExistente(empresa);
													 });
	}
	
	public Empresa getEmpresa(String empresa) throws ElementoNotFound {
		if(this.contieneEmpresa(empresa)) {
			return this.empresas.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(empresa)).collect(Collectors.toList()).get(0);	
		}
		else {
			throw new ElementoNotFound("No existe la empresa " + empresa);
		}
	}
	
	public boolean contieneEmpresa(Empresa empr) {
		return this.contieneEmpresa(empr.getNombre());
	}
	
	public boolean contieneEmpresa(String nombre) {
		return this.empresas.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}
	
	public void guardarEmpresa(Empresa empresa) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.persist(empresa);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public void guardarEmpresas(List<Empresa> empresas) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			empresas.stream().forEach(empresa->{if(empresa.estasGuardada())
													manager.merge(empresa);
												else
													manager.persist(empresa);
												});
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}finally {
			manager.close();
		}
	}
	
	public List<Empresa> cargarEmpresas() {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Empresa> empresas = manager.createQuery("from Empresa", Empresa.class).getResultList();
		manager.close();
		return empresas;
	}
	
	public List<Empresa> cargarEmpresas(String nombre) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		List<Empresa> empresas = manager.createQuery("SELECT t FROM Empresa t WHERE t.nombre = :nombre",Empresa.class)
				.setParameter("nombre", nombre).getResultList();
		manager.close();
		return empresas;
	}
	
	//Pusismos esto ya que es lazy init entonces cuando necesitamos los balances de la empresa usamos esto
	public void inicializarBalances(Empresa empresa) {
		EntityManager manager = PerThreadEntityManagers.getEntityManager();
		PersistenceUnitUtil checkeadorDeLoad = manager.getEntityManagerFactory().getPersistenceUnitUtil();
		if(checkeadorDeLoad.isLoaded(empresa, "balancesSemestrales" ) && checkeadorDeLoad.isLoaded(empresa, "balancesAnuales"))
		{
			manager.close();
			return;
		}
		@SuppressWarnings("unchecked")
		List<BalanceAnual> balancesAnuales = manager.createQuery("SELECT b FROM BalanceAnual b WHERE empresa_id = :emp_id")
				.setParameter("emp_id", empresa.getId()).getResultList();
		
		@SuppressWarnings("unchecked")
		List<BalanceSemestral> balancesSemestrales = manager.createQuery("SELECT b FROM BalanceSemestral b WHERE empresa_id = :emp_id")
				.setParameter("emp_id", empresa.getId()).getResultList();
		
		manager.close();
		empresa.setBalancesAnuales(balancesAnuales);
		empresa.setBalancesSemestrales(balancesSemestrales);
	}
	
	//Esto hay q usarlo a evaluar las metodologias
	public void inicializarTodosLosbalances() {
		this.empresas.forEach(empresa-> this.inicializarBalances(empresa));
	}
	
	public void cargarEmpresasGuardadas() {
		this.empresas = this.cargarEmpresas();
	}
	
}

