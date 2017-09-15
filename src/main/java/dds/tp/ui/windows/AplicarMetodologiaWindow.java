package dds.tp.ui.windows;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.AplicarMetodologiaViewModel;

@SuppressWarnings("serial")
public class AplicarMetodologiaWindow extends Window<AplicarMetodologiaViewModel>{

	public AplicarMetodologiaWindow(WindowOwner owner, AplicarMetodologiaViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Aplicar Metodologia");
		
		Panel panelColumna = new Panel(mainPanel);
		panelColumna.setLayout(new ColumnLayout(2));
		//Creacion parte izquierda-------
		Panel panelIzquierdo = new Panel(panelColumna);
		panelIzquierdo.setLayout(new VerticalLayout());
		
		new Label(panelIzquierdo).setText("Metodologia").setWidth(450);
		Selector<AplicarMetodologiaViewModel> selectorMetodologia = new Selector<AplicarMetodologiaViewModel>(panelIzquierdo);
		selectorMetodologia.bindItemsToProperty("metodologias");
		selectorMetodologia.bindValueToProperty("metodologia");
		selectorMetodologia.setWidth(450);
		
		new Label(panelIzquierdo).setText("Condiciones de la Metodologia").setWidth(450);
		Table<AplicarMetodologiaViewModel> tablaCondiciones = new Table<>(panelIzquierdo, AplicarMetodologiaViewModel.class);
		tablaCondiciones.bindItemsToProperty("condiciones");
		tablaCondiciones.bindValueToProperty("condicion");
		tablaCondiciones.setNumberVisibleRows(8);
		
		Column<AplicarMetodologiaViewModel> columnaNombreCondicion = new Column<AplicarMetodologiaViewModel>(tablaCondiciones);
		columnaNombreCondicion.setTitle("Nombre");
		columnaNombreCondicion.bindContentsToProperty("nombre");
		columnaNombreCondicion.setFixedSize(150);
		
		Column<AplicarMetodologiaViewModel> columnaDesc = new Column<AplicarMetodologiaViewModel>(tablaCondiciones);
		columnaDesc.setTitle("Descripcion");
		columnaDesc.bindContentsToProperty("descripcion");
		columnaDesc.setFixedSize(300);
		
		//Creacion parte derecha-------
		Panel panelDerecho = new Panel(panelColumna);
		panelDerecho.setLayout(new VerticalLayout());
		
		new Label(panelDerecho).setText("Listado ordenado de empresas");
		
		Table<AplicarMetodologiaViewModel> tablaResultados = new Table<>(panelDerecho, AplicarMetodologiaViewModel.class);
		tablaResultados.bindItemsToProperty("resultados");
		tablaResultados.bindValueToProperty("resultado");
		tablaResultados.setNumberVisibleRows(15);
		
		Column<AplicarMetodologiaViewModel> columnaNombre = new Column<AplicarMetodologiaViewModel>(tablaResultados);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombreEmpresa");
		columnaNombre.setFixedSize(100);
		
		Column<AplicarMetodologiaViewModel> columnaPuntaje = new Column<AplicarMetodologiaViewModel>(tablaResultados);
		columnaPuntaje.setTitle("Puntaje");
		columnaPuntaje.bindContentsToProperty("puntaje");
		columnaPuntaje.setFixedSize(50);
		
		Column<AplicarMetodologiaViewModel> columnaJusti = new Column<AplicarMetodologiaViewModel>(tablaResultados);
		columnaJusti.setTitle("Justificacion");
		columnaJusti.bindContentsToProperty("justificacion");
		columnaJusti.setFixedSize(300);
		
		new Button(mainPanel)
		.setCaption("Aplicar")
		.onClick(()->this.aplicarMetodologia());
	
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
		new Label(mainPanel).setText("");
	    new Label(mainPanel).setForeground(java.awt.Color.BLUE).bindValueToProperty("aplicarMetodologiaOk");
	}

	private void aplicarMetodologia() {
		this.getModelObject().aplicarMetodologia();
	}

}
