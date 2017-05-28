package dds.tp.evaluador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;
import dds.tp.model.Balance;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

public class Evaluador {
	
	private Indicador indicador;
	private Balance balance;
	private GuardadorIndicadores baulIndicadores;
	
	public Evaluador(Indicador indicador, Balance balance, GuardadorIndicadores indicadores) {
		this.indicador = indicador;
		this.balance = balance;
		this.baulIndicadores = indicadores;
	}
	
	public Float evaluar() throws ParseException {
		InputStream is = new ByteArrayInputStream(indicador.getFormula().getBytes( Charset.defaultCharset() ) );
		GramaticaParser parser = new GramaticaParser(is);
		return parser.evaluar(this);
	}
	
	public float getCuentaValor(String nombre) {
		//Ver donde catchear exceptions q pueden llegar ocurrir
		//Ejemplo q no exista la cuenta, va a tirar indexoutofbounds
		String nombreCuenta = (String) nombre.subSequence(nombre.indexOf("(")+1, nombre.indexOf(")"));
		return balance.getCuentas().stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombreCuenta)).collect(Collectors.toList()).get(0).getValor();
	}
	


	public float getValorIndicador(String nombre) throws ParseException {
		List<Indicador> todosLosIndicadores = baulIndicadores.getIndicadores();
		String nombreIndicador = (String) nombre.subSequence(nombre.indexOf("(")+1, nombre.indexOf(")"));
		Indicador indicadorAUsar = todosLosIndicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombreIndicador)).collect(Collectors.toList()).get(0);
		return new Evaluador(indicadorAUsar, balance, baulIndicadores).evaluar();
	}

}
