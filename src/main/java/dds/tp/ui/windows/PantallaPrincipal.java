package dds.tp.ui.windows;

import dds.tp.ui.complementos.AccionesDisponibles;
import dds.tp.ui.complementos.OpcionDeAccion;
import dds.tp.ui.vm.IOArchivoCuentasViewModel;
import dds.tp.ui.vm.AllCuentasViewModel;
import dds.tp.ui.vm.CuentasViewModel;
import dds.tp.ui.vm.PantallaPrincipalViewModel;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.ObservableUtils;

@SuppressWarnings("serial")
public class PantallaPrincipal extends Window<PantallaPrincipalViewModel> {

	public PantallaPrincipal(WindowOwner parent, PantallaPrincipalViewModel model) {
		super(parent, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Aplicacion de decisiones");
		new Label(mainPanel).setText("Elija que desea hacer:");
		Selector<OpcionDeAccion> accionesDisponibles = new Selector<>(mainPanel);
		accionesDisponibles.setWidth(400);
		accionesDisponibles.bindItemsToProperty("todasLasVentanas");
		accionesDisponibles.bindValueToProperty("ventanaElegida");
		/*Esto hace que se ponga la primera de la lista y no quede vacia*/
		this.getModelObject().setVentanaElegida(this.getModelObject().getTodasLasVentanas().get(0));
		ObservableUtils.firePropertyChanged(this.getModelObject(), "ventanaElegida");
		//-------
		new Label(mainPanel).bindValueToProperty("descripcion");
		new Label(mainPanel).setText("");
		new Button(mainPanel).setCaption("Abrir").onClick(()->this.abrirVentanaElegida());
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
	}
	
	private void abrirVentanaElegida() {
		/*Elegi ponerlo en switch xq nos da mas opciones al tener que cerrarla o abrirla q la opcion anterior del lanzador de ventana*/
		AccionesDisponibles accionElegida = this.getModelObject().getVentanaElegida().getAccionAMostrar();
		switch (accionElegida) {
		case CARGARCUENTAS:
				new CargarCuentasWindow(this, new IOArchivoCuentasViewModel(this.getModelObject().getLector())).open();
			break;
		case CONSULTARCUENTAS:
				List<CuentasViewModel> cuentas = this.getModelObject().getLector().getCuentas().stream().map(ct -> new CuentasViewModel(ct)).collect(Collectors.toList()); 
				new ConsultarCuentasWindow(this, new AllCuentasViewModel(cuentas)).open();
			break;
		default:
			break;
		}
	}
		
}
