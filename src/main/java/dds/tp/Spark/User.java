package dds.tp.Spark;

public class User {
	
	private String nombre;
	private String password;
	
	public User(String user, String password){
		this.setNombre(user);
		this.setPassword(password);
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

}
