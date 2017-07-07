package dds.tp.ui.windows.condiciones;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.condiciones.CargarCondicionComparadoraViewModel;
import dds.tp.ui.vm.condiciones.CargarCondicionSimpleViewModel;

@SuppressWarnings("serial")
public class CargarCondicionComparadoraWindow extends Window<CargarCondicionComparadoraViewModel>{

	public CargarCondicionComparadoraWindow(WindowOwner owner, CargarCondicionComparadoraViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Condicion Comparadora");
		
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
		
		new Label(panel2).setText("Mayor/Menor");
		Selector<CargarCondicionSimpleViewModel> selectorMayorMenor = new Selector<CargarCondicionSimpleViewModel>(panel2);
		selectorMayorMenor.bindItemsToProperty("comparadores");
		selectorMayorMenor.bindValueToProperty("comparadorSeleccionado");
		
		Panel panel3 = new Panel(panelColumna);
		panel3.setLayout(new VerticalLayout());
		
		new Label(panel3).setText("Periodos Hacia Atras");
		new TextBox(panel3).bindValueToProperty("periodosHaciaAtras");
		
		new Button(mainPanel)
		.setCaption("Guardar")
		.onClick(()->this.guardarCondicionComparadora());
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
	}

	private void guardarCondicionComparadora() {
		try {
			this.getModelObject().guardarCondicionComparadora();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
