package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.condiciones.comparadores.Comparador;

//ESTA ES TAXATIVA TE DICE SI SIRVE O NO PARA INVERTIR
public class CondicionSimple extends Condicion {

	private Double valorLimite;
	private int periodos; //esto deberia ser años? //como manejariamos comparar años cuando hay dos periodos en un mismo año?
	private Indicador indicador;
	private Comparador comparador;
	
	public CondicionSimple(String nombre, String descripcion, Indicador indicador, Comparador comparador,Double valorLimite, int periodos) {
		super(nombre, descripcion);
		this.indicador = indicador;
		this.comparador = comparador;
		this.valorLimite = valorLimite;
		this.periodos = periodos;
	}

	public boolean evaluar(Empresa empresa){
		//TIENE QUE VALIDAR SI PARA TODOS LOS ULTIMOS N PERIODOS EL INDICADOR ES MAYOR O MENOR QUE EL VALOR LIMITE
		
		return false;
	}

}
