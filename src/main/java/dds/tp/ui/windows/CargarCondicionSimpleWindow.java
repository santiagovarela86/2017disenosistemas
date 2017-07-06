package dds.tp.ui.windows;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.CargarCondicionSimpleViewModel;

@SuppressWarnings("serial")
public class CargarCondicionSimpleWindow extends Window<CargarCondicionSimpleViewModel>{

	public CargarCondicionSimpleWindow(WindowOwner owner, CargarCondicionSimpleViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Cargar Condicion Simple");
		
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
		
		new Label(panel2).setText("Mayor/Menor");
		Selector<CargarCondicionSimpleViewModel> selectorMayorMenor = new Selector<CargarCondicionSimpleViewModel>(panel2);
		selectorMayorMenor.bindItemsToProperty("simbolosRelacionales");
		selectorMayorMenor.bindValueToProperty("simboloRelacional");
		
		Panel panel3 = new Panel(panelColumna);
		panel3.setLayout(new VerticalLayout());
		
		new Label(panel3).setText("Valor");
		new TextBox(panel3).bindValueToProperty("valor");
		
		Panel panel4 = new Panel(panelColumna);
		panel4.setLayout(new VerticalLayout());
		
		new Label(panel4).setText("Periodos Hacia Atras");
		new TextBox(panel4).bindValueToProperty("periodosHaciaAtras");
		
		new Button(mainPanel)
		.setCaption("Guardar")
		.onClick(()->this.guardarCondicionSimple());
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
	}

	private void guardarCondicionSimple() {
		this.getModelObject().guardarCondicionSimple();
	}
}
