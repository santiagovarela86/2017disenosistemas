package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.FileSelector;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.complementos.Ventana;
import dds.tp.ui.vm.IOArchivoCuentasViewModel;

@SuppressWarnings({ "serial", "unused" })
public class CargarCuentasWindow extends Window<IOArchivoCuentasViewModel> implements Ventana {

	public CargarCuentasWindow(WindowOwner parent, IOArchivoCuentasViewModel model) {
		super(parent, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Cuentas");
		new Label(mainPanel).setText("Ubicacion de archivo a cargar:").setWidth(500);
	    //Se descomenta esto y aparace el textbox de siempre con la ruta del archivo
		/*TextBox textPath = new TextBox(mainPanel);
	    textPath.bindEnabledToProperty("habilitado");
	    textPath.bindValueToProperty("path");*/
		//Se descomenta esto y aparace un file selector
		FileSelector fs = new FileSelector(mainPanel);
		fs.setCaption("Elegir archivo de cuentas");
		fs.bindValueToProperty("path");
		fs.extensions("*.txt");
		new Label(mainPanel).bindValueToProperty("path");
	
	    new Label(mainPanel).setText("");
	    
		new Button(mainPanel).setCaption("Cargar Cuentas").onClick(()->this.getModelObject().cargarCuentas());
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());		
	}

}
