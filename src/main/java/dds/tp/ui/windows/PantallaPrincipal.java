package dds.tp.ui.windows;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.ui.vm.AplicarMetodologiaViewModel;
import dds.tp.ui.vm.CargarCondicionComparadoraViewModel;
import dds.tp.ui.vm.CargarCondicionCrecienteDecrecienteViewModel;
import dds.tp.ui.vm.CargarCondicionEstadisticaViewModel;
import dds.tp.ui.vm.CargarCondicionSimpleViewModel;
import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.CargarIndicadoresViewModel;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
import dds.tp.ui.vm.CrearMetodologiaViewModel;
import dds.tp.ui.vm.PantallaPrincipalViewModel;
import dds.tp.ui.vm.UsarIndicadoresViewModel;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

@SuppressWarnings("serial")
public class PantallaPrincipal extends Window<PantallaPrincipalViewModel> {

	public PantallaPrincipal(WindowOwner parent, PantallaPrincipalViewModel model) {
		super(parent, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Aplicacion de decisiones");

		new Label(mainPanel).setText("Elija la acción que desea realizar: ").setWidth(500);

		Panel panelBotones = new Panel(mainPanel);
		panelBotones.setLayout(new ColumnLayout(2));

		new Button(panelBotones).setCaption("Cargar cuentas").onClick(() -> this.abrirCargarCuentas()).setWidth(220);
		new Button(panelBotones).setCaption("Consultar cuentas").onClick(() -> this.abrirConsultarCuentas()).setWidth(220);
		new Button(panelBotones).setCaption("Cargar Indicadores").onClick(() -> this.abrirCargarIndicadores()).setWidth(220);
		new Button(panelBotones).setCaption("Usar Indicadores").onClick(() -> this.abrirUsarIndicadores()).setWidth(220);
		new Button(panelBotones).setCaption("Crear Metodologia").onClick(() -> this.abrirCrearMetodologia()).setWidth(220);
		new Button(panelBotones).setCaption("Cargar condicion simple").onClick(() -> this.abrirCargarCondicionSimple()).setWidth(220);
		new Button(panelBotones).setCaption("Cargar condicion comparadora").onClick(() -> this.abrirCargarCondicionComparadora()).setWidth(220);
		new Button(panelBotones).setCaption("Cargar condicion estadistica").onClick(() -> this.abrirCargarCondicionEstadistica()).setWidth(220);
		new Button(panelBotones).setCaption("Cargar condicion creciente/decreciente").onClick(() -> this.abrirCargarCondicionCrecienteDecreciente()).setWidth(220);
		new Button(panelBotones).setCaption("Aplicar Metodologia").onClick(() -> this.abrirAplicarMetodologia()).setWidth(220);
		
		new Label(mainPanel).setForeground(java.awt.Color.RED).bindValueToProperty("mensajeError");
		new Label(mainPanel).setText("");
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
		
	}

	private void abrirCrearMetodologia() {
		new CrearMetodologiaWindows(this, new CrearMetodologiaViewModel()).open();
	}

	private void refresh(){
		this.getModelObject().setMensajeError("");
	}

	private void abrirCargarCuentas(){
		this.refresh();
		new CargarCuentasWindow(this, new CargarCuentasViewModel(this.getModelObject().getBaulEmpresas())).open();
	}

	private void abrirConsultarCuentas(){
		try {
			this.comprobarCuentas();
			this.refresh();
			new ConsultarCuentasWindow(this, new ConsultarCuentasViewModel(this.getModelObject().getBaulEmpresas().getEmpresas())).open();
		} catch (ElementNotLoad e){
			this.getModelObject().setMensajeError(e.getMessage());
		}
	}

	private void abrirCargarIndicadores() {
		this.refresh();
		new CargarIndicadoresWindow(this, new CargarIndicadoresViewModel(this.getModelObject().getBaulIndicadores())).open();
	}
	
	private void abrirUsarIndicadores() {
		try {
			this.comprobarCuentas();
			this.comprobarIndicadores();
			this.refresh();
			new UsarIndicadoresWindow(this, new UsarIndicadoresViewModel(this.getModelObject().getBaulEmpresas().getEmpresas(),this.getModelObject().getBaulIndicadores())).open();
		}
		catch(ElementNotLoad ex) {
			this.getModelObject().setMensajeError(ex.getMessage());
		}

	}
	
	private void comprobarCuentas() throws ElementNotLoad{
		if(this.getModelObject().getBaulEmpresas().getEmpresas().isEmpty())
			throw new ElementNotLoad("No hay cuentas cargadas.");
	}

	private void comprobarIndicadores() throws ElementNotLoad{
		if(this.getModelObject().getBaulIndicadores().getIndicadores().isEmpty())
			throw new ElementNotLoad("No hay indicadores cargados.");
	}
	
	private void abrirCargarCondicionSimple(){
		this.refresh();
		new CargarCondicionSimpleWindow(this, new CargarCondicionSimpleViewModel()).open();
	}
	
	private void abrirCargarCondicionComparadora() {
		this.refresh();
		new CargarCondicionComparadoraWindow(this, new CargarCondicionComparadoraViewModel()).open();
	}
	
	private void abrirCargarCondicionEstadistica() {
		this.refresh();
		new CargarCondicionEstadisticaWindow(this, new CargarCondicionEstadisticaViewModel()).open();
	}
	
	private void abrirCargarCondicionCrecienteDecreciente() {
		this.refresh();
		new CargarCondicionCrecienteDecrecienteWindow(this, new CargarCondicionCrecienteDecrecienteViewModel()).open();
	}
	
	private void abrirAplicarMetodologia() {
		this.refresh();
		new AplicarMetodologiaWindow(this, new AplicarMetodologiaViewModel()).open();
	}
}
