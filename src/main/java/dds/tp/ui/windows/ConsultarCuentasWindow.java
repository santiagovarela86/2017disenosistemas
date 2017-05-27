package dds.tp.ui.windows;

import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.ConsultarCuentasViewModel;

@SuppressWarnings("serial")
public class ConsultarCuentasWindow extends Window<ConsultarCuentasViewModel>{

	public ConsultarCuentasWindow(WindowOwner owner, ConsultarCuentasViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Consultar Cuentas");
		new Label(mainPanel).setText("Seleccione una empresa").setWidth(400);
		Selector<ConsultarCuentasViewModel> selecEmpresa = new Selector<ConsultarCuentasViewModel>(mainPanel);
		selecEmpresa.bindItemsToProperty("empresas");
		selecEmpresa.bindValueToProperty("empresa");
		this.getModelObject().setEmpresa(this.getModelObject().getEmpresas().get(0));
		new Label(mainPanel).setText("Balance de ").setWidth(400);
		Selector<ConsultarCuentasViewModel> selecBalance = new Selector<ConsultarCuentasViewModel>(mainPanel);
		selecBalance.bindItemsToProperty("empresa.balances");
		selecBalance.bindValueToProperty("balance");
		
		
		Table<ConsultarCuentasViewModel> tablaCuentas = new Table<>(mainPanel, ConsultarCuentasViewModel.class);
		tablaCuentas.bindItemsToProperty("balance.cuentas");
		tablaCuentas.bindValueToProperty("cuenta");
		tablaCuentas.setNumberVisibleRows(10);
		Column<ConsultarCuentasViewModel> columnaNombre = new Column<ConsultarCuentasViewModel>(tablaCuentas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		Column<ConsultarCuentasViewModel> columnaValor = new Column<ConsultarCuentasViewModel>(tablaCuentas);
		columnaValor.setTitle("Valor");
		columnaValor.bindContentsToProperty("valor");
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
	}
}
