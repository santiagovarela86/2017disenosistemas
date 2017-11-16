package dds.tp.batch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ArchivoBatch {
	
	public ArchivoBatch() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	private String name;
	private String content;
	
	public Boolean equals(ArchivoBatch a){
		return this.getName().equalsIgnoreCase(a.getName());
	}
	
	public boolean estasGuardado() {
		if(id == null)
			return false;
		else
			return true;
	}

	public String getName() {
		return name;
	}
	
	public String getNombre() {
		return this.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContenido(){
		return this.getContent();
	}

}
