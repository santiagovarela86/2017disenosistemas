package dds.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;

public class GuardadorEmpresas {
	
	private List<Empresa> empresas;
	
	public GuardadorEmpresas() {
		super();
		this.empresas = new ArrayList<>();
	}
	
	public GuardadorEmpresas(List<Empresa> empresas){
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
		try {
			Empresa emprExistente = this.getEmpresa(empr.getNombre());
			for (Balance balance : empr.getBalances()) {
				try {
					emprExistente.addBalance(balance);
				}
				catch(ElementoYaExiste ex) {
					emprExistente.unirBalanceConUnoYaExistente(balance);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
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
		try {
			return this.empresas.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(empresa)).collect(Collectors.toList()).get(0);
		}
		catch (IndexOutOfBoundsException e) {
			throw new ElementoNotFound("Empresa " + empresa  + " no encontrada");
		}
	}
	
	public boolean contieneEmpresa(Empresa empr) {
		return this.empresas.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(empr.getNombre()));
	}
	

	
}

