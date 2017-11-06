package dds.tp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.uqbar.commons.utils.Observable;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.ElementoYaExiste;
import dds.tp.model.metodologia.Metodologia;

@Entity
@Observable
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private List<Indicador> indicadores;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private List<Metodologia> metodologias;
	
	public Usuario(String nombre, String password){
		this.setNombre(nombre);
		this.setPassword(password);
		this.indicadores = new ArrayList<>();
		this.metodologias = new ArrayList<>();
	}
	
	public Usuario(){
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
	
	public void addIndicador(Indicador indicador) {
		if (this.tieneIndicador(indicador.getNombre())) {
			throw new ElementoYaExiste("Ya existe un indicador con este nombre");
		}
		
		this.indicadores.add(indicador);
	}
	
	public void addMetodologia(Metodologia metodologia) {
		if (this.tieneMetodologia(metodologia.getNombre())) {
			throw new ElementoYaExiste("Ya existe un indicador con este nombre");
		}
		
		this.metodologias.add(metodologia);
	}

	private boolean tieneMetodologia(String nombre) {
		return this.metodologias.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}

	public boolean tieneIndicador(String nombre) {
		return this.indicadores.stream().anyMatch(elem -> elem.getNombre().equalsIgnoreCase(nombre));
	}
	
	public Indicador getIndicador(String nombre) throws ElementoNotFound {
		if(!this.tieneIndicador(nombre)){
			throw new ElementoNotFound("No existe el indicador " + nombre);
		}
		return this.indicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList()).get(0);
	}
	
	public boolean estasGuardado() {
		if(id == null)
			return false;
		else
			return true;
	}

}
