package dds.tp.model.condiciones;

import dds.tp.excepciones.CondicionNoCumplida;
import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

public class MasAntiguaQue implements Condicion {

	private Empresa empresaAComparar;
	private String nombre = "Mas Antigua";
	private String descripcion = "Esta condicion se cumple cuando una empresa es mas antigua que otra.";

	public MasAntiguaQue() {
		// TODO Auto-generated constructor stub
	}
	
	private MasAntiguaQue(Empresa empresaAComparar) {
		super();
		this.empresaAComparar = empresaAComparar;
	}

	public MasAntiguaQue setEmpresaAComparar(Empresa empresaAComparar) {
		return new MasAntiguaQue(empresaAComparar);
	}
	
	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}
	
	@Override
	public void esCumplidaEn(Empresa empresa) {
		if(empresa.getAntiguedad() <= this.empresaAComparar.getAntiguedad()){
			throw new CondicionNoCumplida("Esta empresa no es mas antigua que " + this.empresaAComparar.getAntiguedad());
		}
	}
}
