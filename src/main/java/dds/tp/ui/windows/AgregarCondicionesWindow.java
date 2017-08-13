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
		
		new Button(mainPanel).setCaption("Cargar condicion simple").onClick(() -> this.abrirCargarCondicionSimple()).setWidth(200);
		new Button(mainPanel).setCaption("Cargar condicion comparadora").onClick(() -> this.abrirCargarCondicionComparadora()).setWidth(200);
		new Button(mainPanel).setCaption("Cargar condicion estadistica").onClick(() -> this.abrirCargarCondicionEstadistica()).setWidth(200);
		new Button(mainPanel).setCaption("Cargar condicion creciente/decreciente").onClick(() -> this.abrirCargarCondicionCrecienteDecreciente()).setWidth(200);
		//new Button(mainPanel).setCaption("Agregar condicion de Longevidad").onClick(() -> this.agregarCondicionLongevidad()).setWidth(200);
		//new Button(mainPanel).setCaption("Agregar condicion Comparadora de Longevidad").onClick(() -> this.agregarCondicionLongevidadComparadora()).setWidth(200);		
		new Label(mainPanel).setText("").setWidth(400).bindValueToProperty("mensajeOK");
		new Label(mainPanel).setText("").setWidth(400);
		
		new Button(mainPanel)
		.setCaption("Cerrar")
		.onClick(()->this.close());
		
	}
	
	//HAY QUE IMPLEMENTAR LA LONGEVIDAD COMO CONDICION PARA SOPORTARLO EN ESTE MODELO
	/*
	private void agregarCondicionLongevidad(){
		this.getModelObject().setMensajeOK("");
		
		this.getModelObject().getMetodologiaBuilder().agregarCondTaxativa(new CondicionLongevidadSimple("Longevidad Simple", "La empresa tiene mas de 10 años"));
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}
	
	private void agregarCondicionLongevidadComparadora(){
		this.getModelObject().setMensajeOK("");
		
		this.getModelObject().getMetodologiaBuilder().agregarCondPriorizar(new CondicionLongevidadComparadora("Longevidad Comparadora", "Ordena las empresas por antigüedad"));
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}
	*/
	
	private void abrirCargarCondicionSimple(){
		this.getModelObject().setMensajeOK("");
		
		new CargarCondicionSimpleWindow(this, new CargarCondicionSimpleViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}
	
	private void abrirCargarCondicionComparadora() {
		this.getModelObject().setMensajeOK("");
		
		new CargarCondicionComparadoraWindow(this, new CargarCondicionComparadoraViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}
	
	private void abrirCargarCondicionEstadistica() {
		this.getModelObject().setMensajeOK("");
		
		new CargarCondicionEstadisticaWindow(this, new CargarCondicionEstadisticaViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}
	
	private void abrirCargarCondicionCrecienteDecreciente() {
		this.getModelObject().setMensajeOK("");
		
		new CargarCondicionCrecienteDecrecienteWindow(this, new CargarCondicionCrecienteDecrecienteViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
		
		this.getModelObject().setMensajeOK("Se ha agregado exitosamente la condicion");
	}

}
