package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.AgregarCondicionesViewModel;
import dds.tp.ui.vm.condiciones.CargarCondicionComparadoraViewModel;
import dds.tp.ui.vm.condiciones.CargarCondicionCrecienteDecrecienteViewModel;
import dds.tp.ui.vm.condiciones.CargarCondicionEstadisticaViewModel;
import dds.tp.ui.vm.condiciones.CargarCondicionSimpleViewModel;
import dds.tp.ui.windows.condiciones.CargarCondicionComparadoraWindow;
import dds.tp.ui.windows.condiciones.CargarCondicionCrecienteDecrecienteWindow;
import dds.tp.ui.windows.condiciones.CargarCondicionEstadisticaWindow;
import dds.tp.ui.windows.condiciones.CargarCondicionSimpleWindow;

@SuppressWarnings("serial")
public class AgregarCondicionesWindow extends Window<AgregarCondicionesViewModel>{

	public AgregarCondicionesWindow(WindowOwner owner, AgregarCondicionesViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Agregar condiciones");
		
		new Button(mainPanel).setCaption("Cargar condicion simple").onClick(() -> this.abrirCargarCondicionSimple()).setWidth(220);
		new Button(mainPanel).setCaption("Cargar condicion comparadora").onClick(() -> this.abrirCargarCondicionComparadora()).setWidth(220);
		new Button(mainPanel).setCaption("Cargar condicion estadistica").onClick(() -> this.abrirCargarCondicionEstadistica()).setWidth(220);
		new Button(mainPanel).setCaption("Cargar condicion creciente/decreciente").onClick(() -> this.abrirCargarCondicionCrecienteDecreciente()).setWidth(220);
		
		new Label(mainPanel).setText("");
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
	}
	
	private void abrirCargarCondicionSimple(){
		new CargarCondicionSimpleWindow(this, new CargarCondicionSimpleViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
	}
	
	private void abrirCargarCondicionComparadora() {
		new CargarCondicionComparadoraWindow(this, new CargarCondicionComparadoraViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
	}
	
	private void abrirCargarCondicionEstadistica() {
		new CargarCondicionEstadisticaWindow(this, new CargarCondicionEstadisticaViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
	}
	
	private void abrirCargarCondicionCrecienteDecreciente() {
		new CargarCondicionCrecienteDecrecienteWindow(this, new CargarCondicionCrecienteDecrecienteViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
	}

}
