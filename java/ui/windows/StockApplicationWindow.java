package ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;

import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;

import ui.vm.AlgoViewModel;

public class StockApplicationWindow extends SimpleWindow<AlgoViewModel> {

	public StockApplicationWindow(WindowOwner parent) {
		super(parent, new AlgoViewModel());
	}

	@Override
	protected void addActions(Panel panelActions) {
		new Button(panelActions).setCaption("Cargar Cuentas").onClick(this::cargarCuentas);
		new Button(panelActions).setCaption("Consultar Cuentas").onClick(this::consultarCuentas);
	}

	protected void createFormPanel(Panel formPanel) {
		this.setTitle("Stock Application");
	}

	protected void cargarCuentas() {
		Dialog<?> dialog = new CargarCuentasWindow(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}
	
	protected void consultarCuentas() {
		Dialog<?> dialog = new ConsultarCuentasWindow(this);
		dialog.open();
		dialog.onAccept(() -> {});
	}

}
