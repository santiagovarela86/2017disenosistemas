package dds.tp.ui.vm;

import java.util.List;

import org.uqbar.commons.utils.Observable;

import dds.tp.model.Empresa;
import dds.tp.model.Metodologia;

@Observable
public class AplicarMetodologiaViewModel {
	List<Metodologia> metodologias;
	Metodologia metodologia;
	List<Empresa> empresas;
	Empresa empresa;
	
	public AplicarMetodologiaViewModel(){
		
	}
	
	public List<Metodologia> getMetodologias(){
		return metodologias;
	}
	
	public Metodologia getMetodologia(){
		return metodologia;
	}
	
	public List<Empresa> getEmpresas(){
		return empresas;
	}
	
	public Empresa getEmpresa(){
		return empresa;
	}
	
	public String getNombre() {
		return empresa.getNombre();
	}

	public void aplicarMetodologia() {
		// TODO Auto-generated method stub
		
	}
}
