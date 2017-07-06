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
		
		Panel panelIzquierdo = new Panel(panelColumna);
		panelIzquierdo.setLayout(new VerticalLayout());
		
		new Label(panelIzquierdo).setText("Metodologia").setWidth(220);
		Selector<AplicarMetodologiaViewModel> selectorMetodologia = new Selector<AplicarMetodologiaViewModel>(panelIzquierdo);
		selectorMetodologia.bindItemsToProperty("metodologias");
		selectorMetodologia.bindValueToProperty("metodologia");
		
		Panel panelDerecho = new Panel(panelColumna);
		panelDerecho.setLayout(new VerticalLayout());
		
		new Label(panelDerecho).setText("Listado ordenado de empresas");
		
		Table<AplicarMetodologiaViewModel> tablaEmpresas = new Table<>(panelDerecho, AplicarMetodologiaViewModel.class);
		tablaEmpresas.bindItemsToProperty("empresas");
		tablaEmpresas.bindValueToProperty("empresa");
		tablaEmpresas.setNumberVisibleRows(15);
		Column<AplicarMetodologiaViewModel> columnaNombre = new Column<AplicarMetodologiaViewModel>(tablaEmpresas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		columnaNombre.setFixedSize(200);
		
		new Button(mainPanel)
		.setCaption("Aplicar")
		.onClick(()->this.aplicarMetodologia());
	
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
	}

	private void aplicarMetodologia() {
		this.getModelObject().aplicarMetodologia();
	}

}
