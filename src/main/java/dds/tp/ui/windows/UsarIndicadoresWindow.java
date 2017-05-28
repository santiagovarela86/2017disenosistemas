package dds.tp.ui.windows;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;

import dds.tp.ui.vm.UsarIndicadoresViewModel;

@SuppressWarnings("serial")
public class UsarIndicadoresWindow extends Window<UsarIndicadoresViewModel> {

	public UsarIndicadoresWindow (WindowOwner owner, UsarIndicadoresViewModel model) {
		super(owner, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Usar indicadores");
		new Label(mainPanel).setText("Seleccione un indicador").setWidth(400);
		Selector<UsarIndicadoresViewModel> selecIndicador = new Selector<UsarIndicadoresViewModel>(mainPanel);
		selecIndicador.bindItemsToProperty("indicadores");
		selecIndicador.bindValueToProperty("indicador");
		this.getModelObject().setIndicador(this.getModelObject().getIndicadores().get(0));
		new Label(mainPanel).setText("Seleccione una empresa").setWidth(400);
		Selector<UsarIndicadoresViewModel> selecEmpresa = new Selector<UsarIndicadoresViewModel>(mainPanel);
		selecEmpresa.bindItemsToProperty("empresas");
		selecEmpresa.bindValueToProperty("empresa");
		this.getModelObject().setEmpresa(this.getModelObject().getEmpresas().get(0));
		new Label(mainPanel).setText("Balance de ").setWidth(400);
		Selector<UsarIndicadoresViewModel> selecBalance = new Selector<UsarIndicadoresViewModel>(mainPanel);
		selecBalance.bindItemsToProperty("empresa.balances");
		selecBalance.bindValueToProperty("balance");
		
		new Label(mainPanel).setText("Resultado");
		new Label(mainPanel).bindValueToProperty("resultado");
		new Button(mainPanel).setCaption("Cerrar").onClick(()->this.close());
		
	}


}
