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
import dds.tp.ui.vm.condiciones.CargarCondicionEstadisticaViewModel;

@SuppressWarnings("serial")
public class CargarCondicionEstadisticaWindow extends Window<CargarCondicionEstadisticaViewModel>{

	public CargarCondicionEstadisticaWindow(WindowOwner owner, CargarCondicionEstadisticaViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Condicion Estadistica");
		
		new Label(mainPanel).setText("Nombre").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("nombreCondicion");
		
		new Label(mainPanel).setText("Descripcion").setWidth(400);
		new TextBox(mainPanel).bindValueToProperty("descripcion");
		
		Panel panelColumna = new Panel(mainPanel);
		panelColumna.setLayout(new ColumnLayout(4));
		
		Panel panel1 = new Panel(panelColumna);
		panel1.setLayout(new VerticalLayout());
		
		new Label(panel1).setText("Indicador");
		new TextBox(panel1).bindValueToProperty("nombreIndicador");
		
		Panel panel2 = new Panel(panelColumna);
		panel2.setLayout(new VerticalLayout());
		
		new Label(panel2).setText("Comparador");
		Selector<CargarCondicionEstadisticaViewModel> selectorMayorMenor = new Selector<CargarCondicionEstadisticaViewModel>(panel2);
		selectorMayorMenor.bindItemsToProperty("comparadores");
		selectorMayorMenor.bindValueToProperty("comparadorSeleccionado");
		
		this.getModelObject().setComparadorSeleccionado(this.getModelObject().getComparadores().get(0));
		
		Panel panel3 = new Panel(panelColumna);
		panel3.setLayout(new VerticalLayout());
		
		new Label(panel3).setText("Valor");
		new TextBox(panel3).bindValueToProperty("valor");
		
		Panel panel4 = new Panel(panelColumna);
		panel4.setLayout(new VerticalLayout());
		
		new Label(panel4).setText("Modo Estadistico");
		Selector<CargarCondicionEstadisticaViewModel> selectorTipoEstadistico = new Selector<CargarCondicionEstadisticaViewModel>(panel4);
		selectorTipoEstadistico.bindItemsToProperty("modosEstadisticos");
		selectorTipoEstadistico.bindValueToProperty("modoSeleccionado");
		
		this.getModelObject().setModoSeleccionado(this.getModelObject().getModosEstadisticos().get(0));
		
		Label mensajeDeError = new Label(mainPanel);
		mensajeDeError.bindValueToProperty("mensajeError");
		mensajeDeError.setForeground(Color.RED);
		mensajeDeError.setWidth(400);
		
		new Button(mainPanel)
		.setCaption("Guardar")
		.onClick(()->this.guardarCondicionEstadistica());
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
	}

	private void guardarCondicionEstadistica() {
		try {
			this.getModelObject().guardarCondicionEstadistica();
			this.close();
		}catch (NumberFormatException e) {
			this.getModelObject().setMensajeError("Comprobar valor que sea un numeros valido (acepta decimales)");
		}catch (ElementoNotFound e) {
			this.getModelObject().setMensajeError("Indicador no existe");
		}catch (PeriodosCantBeCero e) {
			this.getModelObject().setMensajeError("El periodo debe ser mayor a 0");
		}catch (RuntimeException e) {
			this.getModelObject().setMensajeError(e.getMessage());
		}
	}

}
