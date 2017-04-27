package dds.tp.ui.windows;

import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.complementos.Ventana;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
import dds.tp.ui.vm.CuentasViewModel;

@SuppressWarnings("serial")
public class ConsultarCuentasWindow extends Window<ConsultarCuentasViewModel> implements Ventana{

	public ConsultarCuentasWindow(WindowOwner owner, ConsultarCuentasViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Consultar Cuentas");
		
		Table<CuentasViewModel> tablaCuentas = new Table<>(mainPanel, CuentasViewModel.class);
		tablaCuentas.bindItemsToProperty("cuentas");
		tablaCuentas.setNumberVisibleRows(10);
		
		Column<CuentasViewModel> columnaNombre = new Column<CuentasViewModel>(tablaCuentas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		Column<CuentasViewModel> columnaEmpresa = new Column<CuentasViewModel>(tablaCuentas);
		columnaEmpresa.setTitle("Empresa");
		columnaEmpresa.bindContentsToProperty("empresa");
		Column<CuentasViewModel> columnaAnio = new Column<CuentasViewModel>(tablaCuentas);
		columnaAnio.setTitle("Anio");
		columnaAnio.bindContentsToProperty("anio");
		Column<CuentasViewModel> columnaValor = new Column<CuentasViewModel>(tablaCuentas);
		columnaValor.setTitle("Valor");
		columnaValor.bindContentsToProperty("valor");

		
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
	}
}
