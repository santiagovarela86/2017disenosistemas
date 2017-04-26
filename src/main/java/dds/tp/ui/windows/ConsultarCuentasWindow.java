package dds.tp.ui.windows;

import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.complementos.Ventana;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
import dds.tp.model.Cuenta;

@SuppressWarnings("serial")
public class ConsultarCuentasWindow extends Window<ConsultarCuentasViewModel> implements Ventana{

	public ConsultarCuentasWindow(WindowOwner owner, ConsultarCuentasViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Consultar Cuentas");
		
		Table<Cuenta> tablaCuentas = new Table<>(mainPanel, Cuenta.class);
		tablaCuentas.bindItemsToProperty("cuentas");
		tablaCuentas.setNumberVisibleRows(10);
		
		Column<Cuenta> columnaNombre = new Column<Cuenta>(tablaCuentas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		Column<Cuenta> columnaEmpresa = new Column<Cuenta>(tablaCuentas);
		columnaEmpresa.setTitle("Empresa");
		columnaEmpresa.bindContentsToProperty("empresa");
		Column<Cuenta> columnaAnio = new Column<Cuenta>(tablaCuentas);
		columnaAnio.setTitle("Anio");
		columnaAnio.bindContentsToProperty("anio");
		Column<Cuenta> columnaValor = new Column<Cuenta>(tablaCuentas);
		columnaValor.setTitle("Valor");
		columnaValor.bindContentsToProperty("valor");
		
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
	}
}
