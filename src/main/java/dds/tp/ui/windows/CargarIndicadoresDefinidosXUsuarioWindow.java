package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.CargarIndicadoresDefinidosXUsuarioViewModel;

@SuppressWarnings("serial")
public class CargarIndicadoresDefinidosXUsuarioWindow extends SimpleWindow<CargarIndicadoresDefinidosXUsuarioViewModel>{

	public CargarIndicadoresDefinidosXUsuarioWindow(WindowOwner parent, CargarIndicadoresDefinidosXUsuarioViewModel model) {
		super(parent, model);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Carga de indicadores definidos x usu");
		
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Cerrar")
			.onClick(()->this.close());
	}

	

}
