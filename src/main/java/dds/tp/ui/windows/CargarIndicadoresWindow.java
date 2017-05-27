package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.CargarIndicadoresViewModel;

@SuppressWarnings("serial")
public class CargarIndicadoresWindow extends Window<CargarIndicadoresViewModel>{

	public CargarIndicadoresWindow(WindowOwner owner, CargarIndicadoresViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Indicadores Definidos por el Usuario");
		new Label(mainPanel).setText("Introduzca el Nombre del Nuevo Indicador:").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("nombreIndicador");
		new Label(mainPanel).setText("Ingrese a continuación la expresion:").setWidth(400);
		new Label(mainPanel).setText("Ejemplo: cuenta(rsoe)*cuenta(ebitda), cuenta(ebitda)*5").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("expresion");
		new Label(mainPanel).bindValueToProperty("resultado");
		
		new Button(mainPanel)
		.setCaption("Guardar")
		.onClick(()->this.guardarExpresion())
		.bindEnabledToProperty("habilitado");
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
	}

	public void guardarExpresion(){
		this.getModelObject().parsearExpresion();
		
	}
}
