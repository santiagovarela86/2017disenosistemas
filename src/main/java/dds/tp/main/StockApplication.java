package dds.tp.main;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import dds.tp.ui.complementos.AccionesDisponibles;
import dds.tp.ui.complementos.OpcionDeAccion;
import dds.tp.ui.vm.PantallaPrincipalViewModel;
import dds.tp.ui.windows.PantallaPrincipal;

public class StockApplication extends Application {
	
	public static void main(String[] args) {
		new StockApplication().start();
	}
	
	@Override
	protected Window<?> createMainWindow() {
		PantallaPrincipalViewModel viewModel = new PantallaPrincipalViewModel(this.getOpcionesDeAccion());
		return new PantallaPrincipal(this, viewModel);
	}
	
	public List<OpcionDeAccion> getOpcionesDeAccion() {
		List<OpcionDeAccion> opcionesDeAccion = new ArrayList<>();
		opcionesDeAccion.add(new OpcionDeAccion("Cargar cuentas", "Permite cargar cuentas de diferentes empresas.", AccionesDisponibles.CARGARCUENTAS));
		opcionesDeAccion.add(new OpcionDeAccion("Consultar cuentas", "Permite ver cuentas cargadas de diferentes empresas.", AccionesDisponibles.CONSULTARCUENTAS));
		return opcionesDeAccion;
	}

}
