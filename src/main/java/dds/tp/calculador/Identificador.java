package dds.tp.calculador;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.model.Balance;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class Identificador {

	private String nombre;
	
	public Identificador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Valor buscaTuValorEn(Balance balance, RepositorioIndicadores baulIndicadores, Usuario usuario){
		if(balance.contieneCuenta(nombre)){
			return new Numero(balance.getCuenta(nombre).getValor());
		}
		else if(baulIndicadores.contieneIndicador(nombre, usuario.getNombre())){
			return new Numero(baulIndicadores.getIndicador(nombre, usuario.getNombre()).evaluar(balance, baulIndicadores));
		}
		else {
			throw new ElementoNotFound("No se ha encontrado ni cuenta ni indicador con el nombre: " + nombre);
		}
	}
	
	
	
}
