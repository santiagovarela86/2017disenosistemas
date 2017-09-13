package dds.tp.main;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;
import dds.tp.ui.vm.PantallaPrincipalViewModel;
import dds.tp.ui.windows.PantallaPrincipal;

public class StockApplication extends Application {
	
	
	public static void main(String[] args) {
		new StockApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		PantallaPrincipalViewModel viewModel = new PantallaPrincipalViewModel();
		return new PantallaPrincipal(this, viewModel);
	}

}
