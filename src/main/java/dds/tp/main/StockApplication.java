package dds.tp.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dds.tp.model.IOArchivoCuentas;
import dds.tp.ui.complementos.LanzadorDeVentana;
import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
import dds.tp.ui.vm.LanzadorDeVentanasViewModel;
import dds.tp.ui.windows.CargarCuentasWindow;
import dds.tp.ui.windows.ConsultarCuentasWindow;
import dds.tp.ui.windows.PantallaPrincipal;

public class StockApplication extends Application {
	
	File miArchivo = new File("cuentas.txt");
	IOArchivoCuentas lector = new IOArchivoCuentas(miArchivo.getAbsolutePath()); // POR AHORA HARDCODEADO
	
	public static void main(String[] args) {
		new StockApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		LanzadorDeVentanasViewModel viewModel = new LanzadorDeVentanasViewModel(this.getVentanasDisponibles());
		return new PantallaPrincipal(this, viewModel);
	}
	
	public List<LanzadorDeVentana> getVentanasDisponibles() {
		List<LanzadorDeVentana> ventanas = new ArrayList<>();
		ventanas.add(new LanzadorDeVentana("Cargar cuentas", "Permite cargar cuentas de diferentes empresas.", new CargarCuentasWindow(this, new CargarCuentasViewModel(lector))));
		ventanas.add(new LanzadorDeVentana("Consultar cuentas", "Permite ver cuentas cargadas de diferentes empresas.", new ConsultarCuentasWindow(this, new ConsultarCuentasViewModel(lector))));
		return ventanas;
	}

}
