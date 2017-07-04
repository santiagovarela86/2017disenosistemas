package dds.tp.model.condiciones;

import dds.tp.model.Condicion;
import dds.tp.model.Empresa;

public class MenorIndicadorQue implements Condicion  {

	private String nombre = "Menor Resultado de Indicador";
	private String descripcion = "Se cumple si el indicador de la empresa es menor que el resultado del indicador de la empresa a comparar";
	private String indicadorAUsar;
	private Empresa empresaConLaQueCompara;
	
	public MenorIndicadorQue(String indicadorAUsar) {
		super();
		this.indicadorAUsar = indicadorAUsar;
	}
	
	private MenorIndicadorQue(Empresa empresaConLaQueCompara, String indicadorAUsar) {
		super();
		this.empresaConLaQueCompara = empresaConLaQueCompara;
	}

	public MenorIndicadorQue setEmpresaConLaQueCompara(Empresa empresaConLaQueCompara) {
		return new MenorIndicadorQue(empresaConLaQueCompara,this.indicadorAUsar);
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public void esCumplidaEn(Empresa empresa) {
		
	}
}
