package dds.tp.model.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
	
	public void agregarEmpresaYaExistente(Empresa empr) {
		Empresa emprExistente = this.getEmpresa(empr.getNombre());
		for (Balance balance : empr.getTodosLosBalances()) {
			try {
				emprExistente.addBalance(balance);
			}
			catch(ElementoYaExiste ex) {
				emprExistente.unirBalanceConUnoYaExistente(balance);
			}
		}
	}
	
	public void addEmpresas(List<Empresa> empresas) {
		for (Empresa empresa : empresas) {
			try {
				this.addEmpresa(empresa);
			}catch(ElementoYaExiste e) {
				this.agregarEmpresaYaExistente(empresa);
			}
		}
	}
	
	public Empresa getEmpresa(String empresa) throws ElementoNotFound {
		if(this.contieneEmpresa(empresa)) {
			return this.empresas.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(empresa)).collect(Collectors.toList()).get(0);	
		}
		else {
			throw new ElementoNotFound("Empresa " + empresa  + " no encontrada");
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
			//transaction.rollback();
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
		@SuppressWarnings("unchecked")
		List<BalanceAnual> balancesAnuales = manager.createQuery("SELECT b FROM Balance b WHERE tipoBalance = :tbalance AND empresa_id = :emp_id")
				.setParameter("tbalance", "balanceAnual").setParameter("emp_id", empresa.getId()).getResultList();
		@SuppressWarnings("unchecked")
		List<BalanceSemestral> balancesSemestrales = manager.createQuery("SELECT b FROM Balance b WHERE tipoBalance = :tbalance AND empresa_id = :emp_id")
				.setParameter("tbalance", "balanceSemestral").setParameter("emp_id", empresa.getId()).getResultList();
		manager.close();
		empresa.setBalancesAnuales(balancesAnuales);
		empresa.setBalancesSemestrales(balancesSemestrales);
	}
	
}

