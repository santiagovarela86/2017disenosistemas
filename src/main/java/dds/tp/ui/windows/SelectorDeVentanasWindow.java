package dds.tp.ui.windows;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.complementos.LanzadorDeVentana;
import dds.tp.ui.vm.LanzadorDeVentanasViewModel;

@SuppressWarnings("serial")
public class SelectorDeVentanasWindow extends Window<LanzadorDeVentanasViewModel>{

	public SelectorDeVentanasWindow(WindowOwner parent, LanzadorDeVentanasViewModel model) {
		super(parent, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Aplicacion de decisiones");
		new Label(mainPanel).setText("Elija que desea hacer:");
		Selector<LanzadorDeVentana> accionesDisponibles = new Selector<>(mainPanel);
		accionesDisponibles.setWidth(400);
		accionesDisponibles.bindItemsToProperty("todasLasVentanas");
		accionesDisponibles.bindValueToProperty("ventanaElegida");
		new Label(mainPanel).bindValueToProperty("descripcion");
		Panel columnas = new Panel(mainPanel);
		columnas.setLayout(new ColumnLayout(2));
		new Button(columnas).setCaption("Abrir").onClick(()->this.getModelObject().getVentanaElegida().abrirVentana());
		new Button(columnas).setCaption("Cerrar").onClick(()->this.close());
	}

	
}
