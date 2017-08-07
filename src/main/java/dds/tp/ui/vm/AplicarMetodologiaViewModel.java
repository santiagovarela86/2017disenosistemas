package dds.tp.ui.vm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import dds.tp.model.Metodologia;
import dds.tp.model.condiciones.Condicion;
import dds.tp.model.metodologia.ResultadoAnalisis;
import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

@Observable
public class AplicarMetodologiaViewModel {
	private RepositorioMetodologias repoMetodologias;
	private RepositorioEmpresas repoEmpresas;
	private RepositorioIndicadores repoIndicadores;
	private List<ResultadoAnalisis> resultados;
	private Metodologia metodologia;
	private ResultadoAnalisis resultado;
	private Condicion condicion;
	
	public AplicarMetodologiaViewModel(RepositorioMetodologias repoMeto,RepositorioEmpresas repoEmpresas, RepositorioIndicadores repoIndicadores){
		this.repoMetodologias = repoMeto;
		this.repoEmpresas = repoEmpresas;
		this.repoIndicadores = repoIndicadores;
	}
	
	public List<Metodologia> getMetodologias(){
		return this.repoMetodologias.getMetodologias();
	}
	
	public void setMetodologia(Metodologia metodologia) {
		this.metodologia = metodologia;
		ObservableUtils.firePropertyChanged(this, "condiciones");
	}
	
	public Metodologia getMetodologia(){
		return metodologia;
	}

	public ResultadoAnalisis getResultado() {
		return resultado;
	}
	
	public void setResultado(ResultadoAnalisis resultado) {
		this.resultado = resultado;
	}

	public void aplicarMetodologia() {
		this.resultados = this.metodologia.evaluarEn(this.repoEmpresas.getEmpresas(), this.repoIndicadores);
		
	}
	
	public List<ResultadoAnalisis> getResultados(){
		return this.resultados;
	}
	
	public String getNombreEmpresa(){
		return this.resultado.getEmpresa().getNombre();
	}
	
	public String getPuntaje() {
		return this.resultado.getPuntaje()+"";
	}
	
	public String getJustificacion() {
		return this.resultado.getJustificacion();
	}
	
	public List<Condicion> getCondiciones() {
		if(metodologia != null)
			return this.metodologia.getCondiciones();
		else 
			return new ArrayList<Condicion>();
	}
	
	public String getNombre() {
		return this.condicion.getNombre();
	}
	
	public String getDescripcion() {
		return this.condicion.getDescripcion();
	}
	
	public Condicion getCondicion() {
		return condicion;
	}
	
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	
}
