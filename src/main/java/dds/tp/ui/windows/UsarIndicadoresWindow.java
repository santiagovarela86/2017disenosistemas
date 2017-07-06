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

import dds.tp.ui.vm.UsarIndicadoresViewModel;

@SuppressWarnings("serial")
public class UsarIndicadoresWindow extends Window<UsarIndicadoresViewModel> {

	
	public UsarIndicadoresWindow (WindowOwner owner, UsarIndicadoresViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Usar indicadores");
		
		Panel panelcolumn = new Panel(mainPanel);
		panelcolumn.setLayout(new ColumnLayout(2));
		
		Panel panelIzquierda = new Panel(panelcolumn);
		panelIzquierda.setLayout(new VerticalLayout());
		
		// Panel izquierdo creacion--------
		new Label(panelIzquierda).setText("Seleccione un indicador").setWidth(500);
		Selector<UsarIndicadoresViewModel> selecIndicador = new Selector<UsarIndicadoresViewModel>(panelIzquierda);
		selecIndicador.bindItemsToProperty("indicadores");
		selecIndicador.bindValueToProperty("indicador");
		new Label(panelIzquierda).bindValueToProperty("expresion");
		new Label(panelIzquierda).setText("").setWidth(500);
		this.getModelObject().setIndicador(this.getModelObject().getIndicadores().get(0));
		new Label(panelIzquierda).setText("Seleccione una empresa").setWidth(400);
		Selector<UsarIndicadoresViewModel> selecEmpresa = new Selector<UsarIndicadoresViewModel>(panelIzquierda);
		selecEmpresa.bindItemsToProperty("empresas");
		selecEmpresa.bindValueToProperty("empresa");
		this.getModelObject().setEmpresa(this.getModelObject().getEmpresas().get(0));
		new Label(panelIzquierda).setText("Balance de ").setWidth(400);
		Selector<UsarIndicadoresViewModel> selecBalance = new Selector<UsarIndicadoresViewModel>(panelIzquierda);
		selecBalance.bindItemsToProperty("empresa.todosLosBalances");
		selecBalance.bindValueToProperty("balance");
		
		Label tipoResultado = new Label(panelIzquierda);
		tipoResultado.bindForegroundToProperty("color");
		tipoResultado.bindValueToProperty("tipoResultado");
		
		Label resultado = new Label(panelIzquierda);
		resultado.bindValueToProperty("resultado");
		resultado.bindForegroundToProperty("color");
		new Button(panelIzquierda).setCaption("Cerrar").onClick(()->this.close());
		// Panel Derecho creacion---------
		Panel panelDerecha = new Panel(panelcolumn);
		panelDerecha.setLayout(new VerticalLayout());
		
		Label infoTabla = new Label(panelDerecha);
		infoTabla.bindValueToProperty("infoEmpresa");
		Table<UsarIndicadoresViewModel> tablaCuentas = new Table<>(panelDerecha, UsarIndicadoresViewModel.class);
		tablaCuentas.bindItemsToProperty("balance.cuentas");
		tablaCuentas.bindValueToProperty("cuenta");
		tablaCuentas.setNumberVisibleRows(10);
		Column<UsarIndicadoresViewModel> columnaNombre = new Column<UsarIndicadoresViewModel>(tablaCuentas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		columnaNombre.setFixedSize(250);
		Column<UsarIndicadoresViewModel> columnaValor = new Column<UsarIndicadoresViewModel>(tablaCuentas);
		columnaValor.setTitle("Valor");
		columnaValor.bindContentsToProperty("valor");
		columnaValor.setFixedSize(250);
		//---------------------------------
	}


}
