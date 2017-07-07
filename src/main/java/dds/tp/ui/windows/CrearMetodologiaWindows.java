package dds.tp.ui.windows;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.ObservableUtils;

import dds.tp.ui.vm.AgregarCondicionesViewModel;
import dds.tp.ui.vm.CrearMetodologiaViewModel;

@SuppressWarnings("serial")
public class CrearMetodologiaWindows extends Window<CrearMetodologiaViewModel> {

	public CrearMetodologiaWindows(WindowOwner owner, CrearMetodologiaViewModel model) {
		super(owner, model);
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Crear metodologia");
		
		Panel panelcolumn = new Panel(mainPanel);
		panelcolumn.setLayout(new ColumnLayout(2));
		
		// Panel izquierdo creacion--------
		Panel panelIzquierda = new Panel(panelcolumn);
		panelIzquierda.setLayout(new VerticalLayout());
		
		new Label(panelIzquierda).setText("Nombre Metodologia").setWidth(400);
		new TextBox(panelIzquierda).bindValueToProperty("nombreMetodologia");
		new Label(panelIzquierda).setText("").setWidth(300);
		new Button(panelIzquierda).setCaption("Agregar condicion").onClick(()->this.agregarCondicion());
		new Label(panelIzquierda).setText("").setWidth(300);
		new Button(panelIzquierda).setCaption("Borrar condiciones").onClick(()->this.borrarCondiciones());
		new Label(panelIzquierda).setText("").setWidth(300);
		new Button(panelIzquierda).setCaption("Guardar").onClick(()->this.guardar());
		new Button(panelIzquierda).setCaption("Cerrar").onClick(()->this.close());
		
		// Panel Derecho creacion---------
		Panel panelDerecha = new Panel(panelcolumn);
		panelDerecha.setLayout(new VerticalLayout());
		
		new Label(panelDerecha).setText("Condiciones Metodologia");
		Table<CrearMetodologiaViewModel> tablaCuentas = new Table<>(panelDerecha, CrearMetodologiaViewModel.class);
		tablaCuentas.bindItemsToProperty("condiciones");
		tablaCuentas.bindValueToProperty("condicion");
		tablaCuentas.setNumberVisibleRows(10);
		Column<CrearMetodologiaViewModel> columnaNombre = new Column<CrearMetodologiaViewModel>(tablaCuentas);
		columnaNombre.setTitle("Nombre");
		columnaNombre.bindContentsToProperty("nombre");
		columnaNombre.setFixedSize(200);
		Column<CrearMetodologiaViewModel> columnaValor = new Column<CrearMetodologiaViewModel>(tablaCuentas);
		columnaValor.setTitle("Descripcion");
		columnaValor.bindContentsToProperty("descripcion");
		columnaValor.setFixedSize(200);
		//---------------------------------
		
	}

	private void borrarCondiciones() {
		this.getModelObject().getMetodologiaBuilder().borrarCondiciones();
		ObservableUtils.firePropertyChanged(this.getModelObject(), "condiciones");
	}

	private void guardar() {
		this.getModelObject().guardarMetodologia();
		this.close();
	}

	private void agregarCondicion() {
		new AgregarCondicionesWindow(this, new AgregarCondicionesViewModel(
				this.getModelObject().getMetodologiaBuilder(), this.getModelObject().getRepoIndicadores())).open();
		ObservableUtils.firePropertyChanged(this.getModelObject(), "condiciones");
	}

}
