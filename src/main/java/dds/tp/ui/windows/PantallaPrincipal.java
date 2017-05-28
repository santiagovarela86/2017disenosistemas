package dds.tp.ui.windows;

import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.CargarIndicadoresViewModel;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
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

		new Label(mainPanel).setText("Elija la acción que desea realizar: ").setWidth(360);

		Panel panelBotones = new Panel(mainPanel);
		panelBotones.setLayout(new ColumnLayout(4));

		new Button(panelBotones).setCaption("Cargar cuentas").onClick(() -> this.abrirCargarCuentas()).setWidth(130);
		new Button(panelBotones).setCaption("Consultar cuentas").onClick(() -> this.abrirConsultarCuentas()).setWidth(130);
		new Button(panelBotones).setCaption("Cargar Indicadores").onClick(() -> this.abrirCargarIndicadores()).setWidth(130);
		new Button(panelBotones).setCaption("Usar Indicadores").onClick(() -> this.abrirUsarIndicadores()).setWidth(130);

		new Label(mainPanel).setText("");
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
		new Label(mainPanel).setForeground(java.awt.Color.BLUE).bindValueToProperty("archivoCuentasOK");
	}

	private void abrirCargarCuentas(){
		this.getModelObject().setArchivoCuentasOK("");
		new CargarCuentasWindow(this, new CargarCuentasViewModel(this.getModelObject().getEmpresas())).open();
	}

	private void abrirConsultarCuentas(){
		try {
			this.getModelObject().setArchivoCuentasOK("");
			new ConsultarCuentasWindow(this, new ConsultarCuentasViewModel(this.getModelObject().getEmpresas().getEmpresas())).open();
		} catch (Exception e){
			e.printStackTrace();
			this.getModelObject().setArchivoCuentasOK("Por favor cargue un archivo de cuentas antes de consultar");
		}
	}

	private void abrirCargarIndicadores(){
		new CargarIndicadoresWindow(this, new CargarIndicadoresViewModel(this.getModelObject().getIndicadores())).open();
	}
	
	private void abrirUsarIndicadores() {
		try {
			new UsarIndicadoresWindow(this, new UsarIndicadoresViewModel(this.getModelObject().getEmpresas().getEmpresas(),this.getModelObject().getIndicadores())).open();
		}catch(Exception e) {
			e.printStackTrace();
			this.getModelObject().setArchivoCuentasOK("Por favor cargue un archivo de cuentas y cree al menos un indicador");
		}
	}


}
