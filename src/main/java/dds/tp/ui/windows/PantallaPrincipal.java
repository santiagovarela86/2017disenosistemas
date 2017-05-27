package dds.tp.ui.windows;

import dds.tp.ui.vm.CargarCuentasViewModel;
import dds.tp.ui.vm.CargarIndicadoresPrincipalViewModel;
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
		
		new Label(mainPanel).setText("Elija la acción que desea realizar: ").setWidth(500);
		
		Panel panelBotones = new Panel(mainPanel);
		panelBotones.setLayout(new ColumnLayout(4));
		
		new Button(panelBotones).setCaption("Cargar cuentas").onClick(() -> this.abrirCargarCuentas()).setWidth(100);
		new Button(panelBotones).setCaption("Consultar cuentas").onClick(() -> this.abrirConsultarCuentas()).setWidth(110);
		new Button(panelBotones).setCaption("Cargar Indicadores").onClick(() -> this.abrirCargarIndicadoresPrincipal()).setWidth(120);
		new Button(panelBotones).setCaption("Mostrar Indicadores").onClick(() -> this.abrirListarIndicadores()).setWidth(120);
		
		new Label(mainPanel).setText("");
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
		new Label(mainPanel).setForeground(java.awt.Color.BLUE).bindValueToProperty("archivoCuentasOK");
	}
	
	private void abrirCargarCuentas(){
		new CargarCuentasWindow(this, new CargarCuentasViewModel(this.getModelObject().getEmpresas())).open();
	}
	
	private void abrirConsultarCuentas(){
		try {
			this.getModelObject().setArchivoCuentasOK("");
			new ConsultarCuentasWindow(this, new ConsultarCuentasViewModel(this.getModelObject().getEmpresas().getEmpresas())).open();
		} catch (Exception e){
			this.getModelObject().setArchivoCuentasOK("Por favor cargue un archivo de cuentas antes de consultar");
			//e.printStackTrace();
		}
	}
	
	private void abrirCargarIndicadoresPrincipal(){
		new CargarIndicadoresPrincipalWindow(this, new CargarIndicadoresPrincipalViewModel()).open();
	}
	
	private void abrirListarIndicadores(){
		
	}
	

		
}
