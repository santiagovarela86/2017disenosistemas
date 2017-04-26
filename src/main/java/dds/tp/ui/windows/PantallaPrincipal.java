package dds.tp.ui.windows;

import dds.tp.ui.complementos.LanzadorDeVentana;
import dds.tp.ui.vm.LanzadorDeVentanasViewModel;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

@SuppressWarnings("serial")
public class PantallaPrincipal extends Window<LanzadorDeVentanasViewModel> {

	public PantallaPrincipal(WindowOwner parent, LanzadorDeVentanasViewModel model) {
		super(parent, model);
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
		
		new Label(mainPanel).setText("");
		
		new Button(mainPanel).setCaption("Abrir").onClick(()->this.getModelObject().getVentanaElegida().abrirVentana());
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
	}
		
}
