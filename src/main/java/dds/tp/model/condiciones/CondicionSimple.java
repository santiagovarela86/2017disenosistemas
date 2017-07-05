package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionSimple extends Condicion {

	private float valorLimite;
	private int periodos; //esto deberia ser años? //como manejariamos comparar años cuando hay dos periodos en un mismo año?
	
	public CondicionSimple(String nombre, String indicador, String comparador, String descripcion, float valorLimite, int periodos) {
		super(nombre, indicador, comparador, descripcion);
		this.setValorLimite(valorLimite);
		this.setPeriodos(periodos);
	}

	public int getPeriodos() {
		return periodos;
	}

	public void setPeriodos(int periodos) {
		this.periodos = periodos;
	}

	public float getValorLimite() {
		return valorLimite;
	}

	public void setValorLimite(float valorLimite) {
		this.valorLimite = valorLimite;
	}	
	
	public boolean evaluar(Empresa empresa){
		//TIENE QUE VALIDAR SI PARA TODOS LOS ULTIMOS N PERIODOS EL INDICADOR ES MAYOR O MENOR QUE EL VALOR LIMITE
		
		return false;
	}

}
