package dds.tp.ui.windows;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.CargarIndicadoresDefinidosXUsuarioViewModel;
import dds.tp.ui.vm.CargarIndicadoresPredefinidosViewModel;
import dds.tp.ui.vm.CargarIndicadoresPrincipalViewModel;

@SuppressWarnings("serial")
public class CargarIndicadoresPrincipalWindow extends Window<CargarIndicadoresPrincipalViewModel> {
	public CargarIndicadoresPrincipalWindow(WindowOwner parent, CargarIndicadoresPrincipalViewModel model){
		super(parent,model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		setTitle("Carga de indicadores");
		
		new Label(mainPanel).setText("elija el tipo de indicadores a ingresar...").setWidth(400);
		
		new Label(mainPanel).setText("");
		
		Panel panelBotonera = new Panel(mainPanel);
		panelBotonera.setLayout(new HorizontalLayout());
		
		new Button(panelBotonera).setCaption("Indic predefinidos")
							     .onClick(() -> this.abrirCargarIndicadoresPredefinidos())
								 .setWidth(200);
								 
		new Button(panelBotonera).setCaption("Indic definidos x el usuario")
	     						 .onClick(() -> this.abrirCargarIndicadoresDefinidosXUsuario())
	     						 .setWidth(200);
		
		new Label(mainPanel).setText("");
		
		new Button(mainPanel).setCaption("Cerrar").onClick(() -> this.close());
		 
	}
	
	public void abrirCargarIndicadoresPredefinidos() {
		new CargarIndicadoresPredefinidosWindow(this, new CargarIndicadoresPredefinidosViewModel()).open();
	}

	public void abrirCargarIndicadoresDefinidosXUsuario() {
		new CargarIndicadoresDefinidosXUsuarioWindow(this, new CargarIndicadoresDefinidosXUsuarioViewModel()).open();
	}

}