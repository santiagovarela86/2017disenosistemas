package dds.tp.ui.windows;

import org.eclipse.swt.graphics.Color;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Control;
import org.uqbar.arena.widgets.FileSelector;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

import dds.tp.ui.vm.CargarCuentasViewModel;

@SuppressWarnings({ "serial", "unused" })
public class CargarCuentasWindow extends SimpleWindow<CargarCuentasViewModel> {

	public CargarCuentasWindow(WindowOwner parent, CargarCuentasViewModel model) {
		super(parent, model);
	}

	@Override
	public void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Cuentas");
		new Label(mainPanel).setText("Ubicacion de archivo a cargar:").setWidth(500);
		FileSelector fs = new FileSelector(mainPanel);
		fs.setCaption("Elegir archivo de cuentas");
		fs.bindValueToProperty("path");
		fs.extensions("*.txt");
		new Label(mainPanel).bindValueToProperty("path");
	
	    new Label(mainPanel).setText("");
	    
	    new Label(mainPanel).setForeground(java.awt.Color.ORANGE).bindValueToProperty("readFileOK");
	    	    
	    
	}


@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
		.setCaption("Cargar Cuentas")
		.onClick(getModelObject()::cargarCuentas)
		.setAsDefault()
		.disableOnError();
		
		new Button(actionsPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close())
		.setAsDefault()
		.disableOnError();
		
	}
	
	

}
