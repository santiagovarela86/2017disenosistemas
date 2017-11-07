package dds.tp.ui.windows;

import dds.tp.excepciones.ElementNotLoad;
import dds.tp.ui.vm.AplicarMetodologiaViewModel;
import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.CargarIndicadoresViewModel;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
import dds.tp.ui.vm.CrearMetodologiaViewModel;
import dds.tp.ui.vm.PantallaPrincipalViewModel;

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

		new Label(mainPanel).setText("Elija la acción que desea realizar: ").setWidth(400);

		Panel panelBotones = new Panel(mainPanel);
		panelBotones.setLayout(new ColumnLayout(2));

		new Button(panelBotones).setCaption("Importar Cuentas").onClick(() -> this.abrirCargarCuentas()).setWidth(200);
		new Button(panelBotones).setCaption("Consultar cuentas").onClick(() -> this.abrirConsultarCuentas()).setWidth(200);
		new Button(panelBotones).setCaption("Cargar Indicadores").onClick(() -> this.abrirCargarIndicadores()).setWidth(200);
		new Button(panelBotones).setCaption("Usar Indicadores").onClick(() -> this.abrirUsarIndicadores()).setWidth(200);
		new Button(panelBotones).setCaption("Crear Metodologia").onClick(() -> this.abrirCrearMetodologia()).setWidth(200);
		new Button(panelBotones).setCaption("Aplicar Metodologia").onClick(() -> this.abrirAplicarMetodologia()).setWidth(200);
		
		new Label(mainPanel).setForeground(java.awt.Color.RED).bindValueToProperty("mensajeError");
		//new Label(mainPanel).setText("");
		//new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
		new Button(mainPanel).setCaption("Cerrar").onClick(()->System.exit(0));
		
	}

	private void abrirCrearMetodologia() {
		new CrearMetodologiaWindows(this, new CrearMetodologiaViewModel(this.getModelObject().getBaulIndicadores(),this.getModelObject().getRepoMetodologia())).open();
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
			new ConsultarCuentasWindow(this, new ConsultarCuentasViewModel(this.getModelObject().getBaulEmpresas())).open();
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
			//new UsarIndicadoresWindow(this, new UsarIndicadoresViewModel(this.getModelObject().getBaulEmpresas(),this.getModelObject().getBaulIndicadores().obtenerIndicadoresPublicosGuardados())).open();
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
		/*if(this.getModelObject().getBaulIndicadores().obtenerIndicadoresPublicosGuardados().getIndicadores().isEmpty())
			throw new ElementNotLoad("No hay indicadores cargados.");*/
	}
	
	private void abrirAplicarMetodologia() {
		try {
			this.comprobarCuentas();
			this.refresh();
			new AplicarMetodologiaWindow(this, 
					new AplicarMetodologiaViewModel(this.getModelObject().getRepoMetodologia(),
							this.getModelObject().getBaulEmpresas(),this.getModelObject().getBaulIndicadores())).open();
		}catch (ElementNotLoad e) {
			this.getModelObject().setMensajeError("No hay empresas cargadas");
		}

	}
}
