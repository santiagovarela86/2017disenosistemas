package dds.tp.ui.windows.condiciones;

import java.awt.Color;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.excepciones.ElementoNotFound;
import dds.tp.excepciones.PeriodosCantBeCero;
import dds.tp.ui.vm.condiciones.CargarCondicionCrecienteDecrecienteViewModel;

@SuppressWarnings("serial")
public class CargarCondicionCrecienteDecrecienteWindow extends Window<CargarCondicionCrecienteDecrecienteViewModel>{

	public CargarCondicionCrecienteDecrecienteWindow(WindowOwner owner, CargarCondicionCrecienteDecrecienteViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Condicion Creciente/Decreciente");
		
		new Label(mainPanel).setText("Nombre").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("nombreCondicion");
		
		new Label(mainPanel).setText("Descripcion").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("descripcion");
		
		Panel panelColumna = new Panel(mainPanel);
		panelColumna.setLayout(new ColumnLayout(3));
		
		Panel panel1 = new Panel(panelColumna);
		panel1.setLayout(new VerticalLayout());
		
		new Label(panel1).setText("Indicador");
		new TextBox(panel1).bindValueToProperty("nombreIndicador");
		
		Panel panel2 = new Panel(panelColumna);
		panel2.setLayout(new VerticalLayout());
		
		new Label(panel2).setText("Comparador");
		Selector<CargarCondicionCrecienteDecrecienteViewModel> selectorMayorMenor = new Selector<CargarCondicionCrecienteDecrecienteViewModel>(panel2);
		selectorMayorMenor.bindItemsToProperty("comparadores");
		selectorMayorMenor.bindValueToProperty("comparadorSeleccionado");
		
		this.getModelObject().setComparadorSeleccionado(this.getModelObject().getComparadores().get(0));
		
		Panel panel3 = new Panel(panelColumna);
		panel3.setLayout(new VerticalLayout());
		
		new Label(panel3).setText("Periodos Hacia Atras");
		new TextBox(panel3).bindValueToProperty("periodosHaciaAtras");
		
		
		Label mensajeDeError = new Label(mainPanel);
		mensajeDeError.bindValueToProperty("mensajeError");
		mensajeDeError.setForeground(Color.RED);
		mensajeDeError.setWidth(400);
		
		new Button(mainPanel)
		.setCaption("Guardar")
		.onClick(()->this.guardarCondicionCrecienteDecreciente());
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
	}

	private void guardarCondicionCrecienteDecreciente() {
		try {
			this.getModelObject().guardarCondicionCrecienteDecreciente();
			this.close();
		}catch (NumberFormatException e) {
			this.getModelObject().setMensajeError("Comprobar periodos hacia atras que sea un numero entero");
		}catch (ElementoNotFound e) {
			this.getModelObject().setMensajeError("Indicador no existe");
		}catch (PeriodosCantBeCero e) {
			this.getModelObject().setMensajeError("El periodo debe ser mayor a 0");
		}catch (RuntimeException e) {
			this.getModelObject().setMensajeError(e.getMessage());
		}
	}

}
