package dds.tp.ui.windows;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.MainWindow;

import dds.tp.ui.complementos.LanzadorDeVentana;
import dds.tp.ui.vm.CuentaViewModel;
import dds.tp.ui.vm.LanzadorDeVentanasViewModel;

@SuppressWarnings("serial")
public class MainWindows extends MainWindow<Object> {
	
	public MainWindows(Object model) {
		super(model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		new SelectorDeVentanasWindow(this, new LanzadorDeVentanasViewModel(this.getVentanasDisponibles())).open();
		this.close();
	}

	private List<LanzadorDeVentana> getVentanasDisponibles() {
		List<LanzadorDeVentana> ventanas = new ArrayList<>();
		ventanas.add(new LanzadorDeVentana("Cargar cuentas", "Permite cargar cuentas de diferentes empresas.", new CargarCuentasWindow(this, new CuentaViewModel())));
		ventanas.add(new LanzadorDeVentana("Consultar cuentas", "Permite ver cuentas cargadas de diferentes empresas.", new ConsultarCuentasWindow(this, new CuentaViewModel())));
		return ventanas;
	}
}
