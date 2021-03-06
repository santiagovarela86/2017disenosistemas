package dds.tp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbar.commons.utils.Observable;
import dds.tp.jpa.converters.RepoIndicadoresConverter;
import dds.tp.model.metodologia.Metodologia;
import dds.tp.model.repositorios.RepositorioIndicadores;
import dds.tp.model.repositorios.RepositorioMetodologias;

@Entity
@Observable
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	@Convert(converter=RepoIndicadoresConverter.class)
	private List<Indicador> indicadores;

	@Transient
	private RepositorioIndicadores repoIndicadores;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private List<Metodologia> metodologias;
	@Transient
	private RepositorioMetodologias repoMetodologias;
	
	public Usuario(String nombre, String password){
		this.setNombre(nombre);
		this.setPassword(password);
		this.repoIndicadores = new RepositorioIndicadores();
		this.indicadores = this.repoIndicadores.getIndicadores();
		this.repoMetodologias = new RepositorioMetodologias();
		this.metodologias = this.repoMetodologias.getMetodologias();
	}
	
	public Usuario(){
		
	}
	
	public Long getId() {
		return id;
	}
	
	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public void inicializarRepos(){
		this.repoMetodologias = new RepositorioMetodologias();
		this.repoMetodologias.setMetodologias(metodologias);
		this.repoIndicadores = new RepositorioIndicadores();
		this.repoIndicadores.setIndicadores(indicadores);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addIndicador(Indicador indicador) {
		this.repoIndicadores.addIndicador(indicador);
	}
	
	public void addMetodologia(Metodologia metodologia) {
		this.repoMetodologias.addMetodologia(metodologia);
	}

	public boolean tieneMetodologia(String nombre) {
		return this.repoMetodologias.contieneMetodologia(nombre);
	}

	public boolean tieneIndicador(String nombre) {
		return this.repoIndicadores.contieneIndicador(nombre);
	}
	
	public Indicador getIndicador(String nombre) {
		return this.repoIndicadores.getIndicador(nombre);
	}
	
	public Metodologia getMetodologia(String nombre) {
		return this.repoMetodologias.getMetodologia(nombre);
	}
	
	public boolean estasGuardado() {
		if(id == null)
			return false;
		else
			return true;
	}

	public RepositorioIndicadores getRepoIndicadores() {
		return this.repoIndicadores;
	}

}
