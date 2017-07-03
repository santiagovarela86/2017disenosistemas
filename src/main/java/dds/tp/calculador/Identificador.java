package dds.tp.calculador;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Balance;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Identificador {

	private String nombre;
	
	public Identificador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Valor buscaTuValorEn(Balance balance, RepositorioIndicadores baulIndicadores){
		if(balance.contieneCuenta(nombre)){
			return new Numero(balance.getCuenta(nombre).getValor());
		}
		else if(baulIndicadores.contieneIndicador(nombre)){
			return new Numero(baulIndicadores.getIndicador(nombre).evaluar(balance, baulIndicadores));
		}
		else {
			throw new ElementoNotFound("No se ha encontrado ni cuenta ni indicador con el nombre: " + nombre);
		}
	}
	
	
	
}
