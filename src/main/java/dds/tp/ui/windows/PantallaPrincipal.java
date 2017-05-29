package dds.tp.ui.windows;

import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.CargarIndicadoresViewModel;
import dds.tp.ui.vm.ConsultarCuentasViewModel;
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

		new Label(mainPanel).setText("Elija la acción que desea realizar: ").setWidth(360);

		Panel panelBotones = new Panel(mainPanel);
		panelBotones.setLayout(new ColumnLayout(3));

		new Button(panelBotones).setCaption("Cargar cuentas").onClick(() -> this.abrirCargarCuentas()).setWidth(120);
		new Button(panelBotones).setCaption("Consultar cuentas").onClick(() -> this.abrirConsultarCuentas()).setWidth(120);
		new Button(panelBotones).setCaption("Cargar Indicadores").onClick(() -> this.abrirCargarIndicadores()).setWidth(120);

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
			this.getModelObject().setArchivoCuentasOK("Por favor cargue un archivo de cuentas antes de consultar");
		}
	}

	private void abrirCargarIndicadores(){
		new CargarIndicadoresWindow(this, new CargarIndicadoresViewModel()).open();
	}

}
