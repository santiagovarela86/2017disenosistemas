package dds.tp.model;

import java.util.ArrayList;
import java.util.List;

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
	
	public void addEmpresa(Empresa emp) {
		this.empresas.add(emp);
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
}
