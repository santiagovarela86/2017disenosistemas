package dds.tp.evaluador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import dds.tp.calculador.Calculador;
import dds.tp.excepciones.CuentaNotFound;
import dds.tp.excepciones.IndicadorNotFound;
import dds.tp.lexer.GramaticaParser;
import dds.tp.lexer.ParseException;
import dds.tp.model.Balance;
import dds.tp.model.GuardadorIndicadores;
import dds.tp.model.Indicador;

public class EvaluadorIndicador {
	
	private Indicador indicador;
	private Balance balance;
	private GuardadorIndicadores baulIndicadores;
	
	public EvaluadorIndicador(Indicador indicador, Balance balance, GuardadorIndicadores indicadores) {
		this.indicador = indicador;
		this.balance = balance;
		this.baulIndicadores = indicadores;
	}
	
	public Float evaluar() throws ParseException, CuentaNotFound, IndicadorNotFound {
		InputStream is = new ByteArrayInputStream(indicador.getFormula().getBytes( Charset.defaultCharset() ) );
		GramaticaParser parser = new GramaticaParser(is);
		Calculador calc = new Calculador(parser.aevaluar(this));
		return calc.obtenerResultado();
	}
	
	public float getCuentaValor(String nombre) throws CuentaNotFound {
		String nombreCuenta = (String) nombre.subSequence(nombre.indexOf("(")+1, nombre.indexOf(")"));
		try {
			return balance.getCuentas().stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombreCuenta)).collect(Collectors.toList()).get(0).getValor();
		}
		catch (IndexOutOfBoundsException ex)
		{
			throw new CuentaNotFound(nombreCuenta,this.indicador.getNombre());
		}
	}
	
	public float getValorIndicador(String nombre) throws ParseException, CuentaNotFound, IndicadorNotFound {
		List<Indicador> todosLosIndicadores = baulIndicadores.getIndicadores();
		String nombreIndicador = (String) nombre.subSequence(nombre.indexOf("(")+1, nombre.indexOf(")"));
		try {
			Indicador indicadorAUsar = todosLosIndicadores.stream().filter(elem -> elem.getNombre().equalsIgnoreCase(nombreIndicador)).collect(Collectors.toList()).get(0);
			return new EvaluadorIndicador(indicadorAUsar, balance, baulIndicadores).evaluar();
		}catch(IndexOutOfBoundsException ex){
			throw new IndicadorNotFound(nombreIndicador, this.indicador.getNombre());
		}
	}

}
