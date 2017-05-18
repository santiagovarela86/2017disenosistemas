package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.CargarIndicadoresPredefinidosViewModel;

@SuppressWarnings("serial")
public class CargarIndicadoresPredefinidosWindow extends SimpleWindow<CargarIndicadoresPredefinidosViewModel>{
	
	public CargarIndicadoresPredefinidosWindow(WindowOwner parent, CargarIndicadoresPredefinidosViewModel model) {
		super(parent, model);
	}
	
	@Override
	protected void createFormPanel(Panel mainPanel) {
		setTitle("Carga de indicadores predefinidos");	
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Cerrar")
			.onClick(()->this.close());
	}
}
